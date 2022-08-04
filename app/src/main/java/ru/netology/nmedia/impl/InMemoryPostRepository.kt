package ru.netology.nmedia.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = List(500) { index ->
            Post(
                idPost = index + 1L,
                authorPost = "Нетология. Университет интернет-профессий будущего",
                textPost = "Текст поста № ${index + 1}",
                datePost = "21 мая в 18:36",
                likes = 999,
                share = 0,
                views = 0,
                likedByMe = false
            )
        }
        data = MutableLiveData(initialPosts)
    }

    override fun like(idPost: Long) {

        posts = posts.map { post ->
            if (idPost == post.idPost) post.copy(
                likedByMe = !post.likedByMe,
                likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
            ) else post
        }
    }

    override fun share(idPost: Long) {
        posts = posts.map { post ->
            if (idPost == post.idPost) post.copy(share = post.share + 1) else post
        }
    }

    override fun view(idPost: Long) {

        posts = posts.map { post ->
            if (idPost == post.idPost) post.copy(views = post.views + 1) else post
        }
    }
}