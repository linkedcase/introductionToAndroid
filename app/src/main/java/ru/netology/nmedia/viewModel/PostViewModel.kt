package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.impl.SharedPrefsPostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {

    private val repository: PostRepository = SharedPrefsPostRepository(application)

    val data get() = repository.data

    val sharePostContent = SingleLiveEvent<String>()
    val playVideoLink = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<String>()

    val currentPost = MutableLiveData<Post?>(null)

    fun onAddClicked() {
        navigateToPostContentScreenEvent.call()
    }

    fun onSaveButtonClicked(content: String) {

        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            textPost = content
        ) ?: Post(
            idPost = PostRepository.NEW_POST_ID,
            authorPost = "Новый пользователь",
            textPost = content,
            datePost = "Сегодня",
            likes = 0,
            share = 0,
            views = 0
        )
        repository.save(post)
        currentPost.value = null
    }

    // region PostInteractionListener

    override fun onLikeClicked(post: Post) = repository.like(post.idPost)

    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.textPost
    }

    override fun onViewClicked(post: Post) = repository.view(post.idPost)

    override fun onRemoveClicked(post: Post) = repository.delete(post.idPost)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = post.textPost
    }

    override fun onPlayClicked(post: Post) {
        playVideoLink.value = post.videoLink
    }

    override fun onEditCancelClicked(): String {
        return ""
    }
    // endregion PostInteractionListener
}