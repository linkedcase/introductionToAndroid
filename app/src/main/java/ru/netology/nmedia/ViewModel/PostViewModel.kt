package ru.netology.nmedia.ViewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.Impl.InMemoryPostRepository
import ru.netology.nmedia.data.PostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeClicKed() = repository.like()

    fun isShared() = repository.share()

    fun isViewed() = repository.view()

}