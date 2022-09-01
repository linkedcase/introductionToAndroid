package ru.netology.nmedia.adapter

import ru.netology.nmedia.Post

interface PostInteractionListener {

    fun onLikeClicked(post: Post)

    fun onShareClicked(post: Post)

    fun onViewClicked(post: Post)

    fun onRemoveClicked(post: Post)

    fun onEditClicked(post: Post)

    fun onEditCancelClicked(): String
}