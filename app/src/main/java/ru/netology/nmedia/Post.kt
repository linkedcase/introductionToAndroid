package ru.netology.nmedia

data class Post(
    var likes: Int,
    var share: Int,
    var views: Int,
    var likedByMe: Boolean = false
)