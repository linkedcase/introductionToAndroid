package ru.netology.nmedia

data class Post(
    val idPost: Long,
    val authorPost: String,
    val textPost: String,
    val datePost: String,
    val likes: Int,
    val share: Int,
    val views: Int,
    val likedByMe: Boolean = false
)