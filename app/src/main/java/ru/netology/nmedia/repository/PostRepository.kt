package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.netology.nmedia.dto.Post
import kotlin.Exception

interface PostRepository {
    val data: Flow<List<Post>>
    suspend fun getAll()
    suspend fun likeById(id: Long)
    suspend fun save(post: Post)
    suspend fun disLikeById(id: Long)
    suspend fun removeById(id: Long)
    suspend fun getNewPosts()
    fun getNewerCount(id: Long): Flow<Int>

}
