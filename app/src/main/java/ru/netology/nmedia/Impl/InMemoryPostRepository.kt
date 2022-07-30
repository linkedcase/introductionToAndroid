package ru.netology.nmedia.Impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.getLikeIconResId

class InMemoryPostRepository : PostRepository {

    override val data = MutableLiveData(
        Post(
            idPost = 1L,
            authorPost = "Нетология. Университет интернет-профессий будущего",
            textPost = "Привет, это новая Нетология! Когда-то Нетология начиналась",
            datePost = "21 мая в 18:36",
            likes = 999,
            share = 15,
            views = 1_000
        )
    )

    override fun like() {

        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        } //вытаскиваем Post и требуем, чтобы он был не null, т.к. из LiveData может прийти null

        val likedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe,
            likes = if (currentPost.likedByMe) currentPost.likes - 1 else currentPost.likes + 1
        )
        getLikeIconResId(likedPost.likedByMe)
        data.value = likedPost
    }

    override fun share() {

        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }

        val sharedPost = currentPost.copy(
            share = currentPost.share + 1
        )

        data.value = sharedPost
    }

    override fun view() {

        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }

        val viewedPost = currentPost.copy(
            views = currentPost.views + 1
        )

        data.value = viewedPost
    }
}