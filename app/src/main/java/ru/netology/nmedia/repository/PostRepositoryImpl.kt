package ru.netology.nmedia.repository

import retrofit2.Call
import retrofit2.Response
import ru.netology.nmedia.PostsApi
import ru.netology.nmedia.dto.Post


class PostRepositoryImpl : PostRepository {

    override fun getAllAsync(callback: PostRepository.Callback<List<Post>>) {
        PostsApi.retrofitService.getAll().enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onResponse(
                call: retrofit2.Call<List<Post>>,
                response: retrofit2.Response<List<Post>>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(java.lang.RuntimeException("error " + response.code().toString()))
                    return
                }
                callback.onSuccess(
                    response.body() ?: throw java.lang.RuntimeException("body in null")
                )
            }

            override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                callback.onError(java.lang.RuntimeException(t))
            }
        })
    }

    override fun likeById(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.likeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(java.lang.RuntimeException("error " + response.code().toString()))
                    return
                }
                callback.onSuccess(response.body() ?: throw java.lang.RuntimeException("error"))
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(java.lang.RuntimeException(t))
            }
        })
    }

    override fun disLikeById(id: Long, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.disLikeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(java.lang.RuntimeException("error " + response.code().toString()))
                    return
                }
                callback.onSuccess(response.body() ?: throw java.lang.RuntimeException("error"))
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(java.lang.RuntimeException(t))
            }
        })

    }

    override fun save(post: Post, callback: PostRepository.Callback<Post>) {
        PostsApi.retrofitService.save(post).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(java.lang.RuntimeException("error " + response.code().toString()))
                    return
                }
                callback.onSuccess(response.body() ?: throw java.lang.RuntimeException("rror"))
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(java.lang.RuntimeException(t))
            }
        })
    }

    override fun removeById(id: Long, callback: PostRepository.Callback<Unit>) {
        PostsApi.retrofitService.removeById(id).enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (!response.isSuccessful) {
                    callback.onError(java.lang.RuntimeException("error " + response.code().toString()))
                    return
                }
                callback.onSuccess(Unit)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                callback.onError(java.lang.RuntimeException(t))
            }
        })
    }
}
