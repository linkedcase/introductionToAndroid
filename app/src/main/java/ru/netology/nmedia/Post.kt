package ru.netology.nmedia

import kotlinx.serialization.Serializable


@Serializable
data class Post(
    val idPost: Long,
    val authorPost: String,
    val textPost: String,
    val datePost: String,
    val likes: Int,
    val share: Int,
    val views: Int,
    val likedByMe: Boolean = false,
    var videoLink: String = ""
)