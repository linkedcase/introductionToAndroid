package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.impl.InMemoryPostRepository
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data get() = repository.data

    // именуем, что произошло какое-то событие
    fun onLikeClicked(post: Post) = repository.like(post.idPost)

    fun isShared(post: Post) = repository.share(post.idPost)

    fun isViewed(post: Post) = repository.view(post.idPost)
}