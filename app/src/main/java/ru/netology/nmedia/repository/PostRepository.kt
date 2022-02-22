package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post
import kotlin.Exception

interface PostRepository {
    fun getAll(): List<Post>
    fun getAllAsync(callback: GetAllCallback )
    fun likeById(id: Long, callback: PostCallback)
    fun save(post: Post, callback: PostCallback)
    fun disLikeById(id: Long, callback: PostCallback)
    fun removeById(id: Long, callback: ByIdCallback)

    interface GetAllCallback {
        fun onSuccess(posts: List<Post>) {}
        fun onError(e: Exception) {}
    }

    interface PostCallback {
        fun onSuccess(posts: Post) {}
        fun onError(e: Exception) {}
    }

    interface ByIdCallback {
        fun onSuccess(id: Long) {}
        fun onError(e: Exception) {}
    }
}
