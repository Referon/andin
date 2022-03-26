package ru.netology.nmedia.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.RetryTypes
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply {
                        textArg = post.content
                    }
                )
            }

            override fun onLike(post: Post) {
                if (!post.likedByMe) {
                    viewModel.likeById(post.id)
                    viewModel.loadPosts()
                } else {
                    viewModel.disLikeById(post.id)
                    viewModel.loadPosts()
                }
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }

                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        })
        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner, { state ->
            adapter.submitList(state.posts)
            binding.emptyText.isVisible = state.empty
        })

        viewModel.dataState.observe(viewLifecycleOwner, { state ->
            binding.progress.isVisible = state.loading
            binding.errorGroup.isVisible = state.error
            binding.refresh.isRefreshing = state.refreshing
            if (state.error) {
                Snackbar.make(
                    requireView(),
                    state.errorCode,
                    BaseTransientBottomBar.LENGTH_LONG
                ).setAction(R.string.retry_loading) {
                    when (state.retryType) {
                        RetryTypes.SAVE -> viewModel.retrySave(state.retryPost)
                        RetryTypes.REMOVE -> viewModel.removeById(state.retryId)
                        RetryTypes.LIKE -> viewModel.likeById(state.retryId)
                        RetryTypes.DISLIKE -> viewModel.disLikeById(state.retryId)
                        else -> viewModel.loadPosts()
                    }
                }
                    .show()
            }
        })
        binding.newPosts.visibility = View.GONE
        viewModel.newerCount.observe(viewLifecycleOwner) {
            val text: String = binding.newPosts.text.toString() + it.toString()
            binding.newPosts.text = text
            if (it > 0) {

                binding.newPosts.visibility = View.VISIBLE
            }
        }

        binding.newPosts.setOnClickListener {
            binding.newPosts.visibility = View.GONE
            viewModel.loadNewPosts()
            binding.list.smoothScrollToPosition(0)
        }




        binding.retryButton.setOnClickListener {
            viewModel.loadPosts()
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        binding.refresh.setColorSchemeResources(
            android.R.color.holo_blue_bright
        )

        binding.refresh.setOnRefreshListener {
            viewModel.loadPosts()
        }

        return binding.root
    }
}
