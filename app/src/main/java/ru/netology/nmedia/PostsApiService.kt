package ru.netology.nmedia

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.nmedia.dto.Post

private const val BASE_URL = "http://10.0.2.2:9999/api/slow/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface PostsApiService {
    @GET("posts")
    suspend fun getAll(): Response<List<Post>>

    @GET("posts/{id}")
    suspend fun getById(@Path("id") id: Long):  Response<Post>

    @POST("posts/{id}/likes")
    suspend fun likeById(@Path("id") id: Long):  Response<Post>

    @DELETE("posts/{id}/likes")
    suspend fun disLikeById(@Path("id") id: Long):  Response<Post>

    @POST("posts")
    suspend fun save(@Body post: Post):  Response<Post>

    @DELETE("posts/{id}")
    suspend fun removeById (@Path("id") id: Long):  Response<Unit>
}
object PostsApi {
    val retrofitService: PostsApiService by lazy {
        retrofit.create(PostsApiService::class.java)
    }
}