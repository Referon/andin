package ru.netology.nmedia.model

import ru.netology.nmedia.RetryTypes
import ru.netology.nmedia.dto.Post

data class FeedModel(
    val posts: List<Post> = emptyList(),
    val empty: Boolean = false,

)

data class FeedModelState(
    val refreshing: Boolean = false,
    val errorCode: String = "",
    val error: Boolean = false,
    val loading: Boolean = false,
    val retryId: Long = 0,
    val retryType: RetryTypes? = null,
    val retryPost: Post? = null

)
