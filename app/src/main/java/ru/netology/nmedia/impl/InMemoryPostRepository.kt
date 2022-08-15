package ru.netology.nmedia.impl

import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

class InMemoryPostRepository : PostRepository {

    private var nextIdPost = GENERETED_POST_AMOUNT.toLong()

    private var posts
        get() = checkNotNull(data.value)
        set(value) {
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val initialPosts = List(GENERETED_POST_AMOUNT) { index ->
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

    override fun delete(idPost: Long) {
        posts = posts.filter { post ->
            idPost != post.idPost
        }
    }

    override fun save(post: Post) {
        if (post.idPost == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(idPost = ++nextIdPost)
        ) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.idPost == post.idPost) post else it
        }
    }

    private companion object {
        const val GENERETED_POST_AMOUNT = 1000
    }
}

@DrawableRes
fun getLikeIconResId(liked: Boolean) =
    if (liked) R.drawable.ic_liked_by_me_24dp else R.drawable.ic_like_24dp

fun printQuantity(quantity: Int): String {

    val hundreds = quantity % 1_000
    val thousands = quantity % 1_000_000
    val hundredsToText = (hundreds / 100).toString()
    val thousandsToText = (thousands / 100_000).toString()

    val value = when {
        quantity < 1_000 -> quantity
        quantity in 1_000..999_999 -> quantity / 1_000
        else -> quantity / 1_000_000
    }.toString()

    val result = when {
        quantity in 1_000..9_999 && hundreds < 100 -> value + "K"
        quantity in 1_000..9_999 -> value + '.' + hundredsToText + "K"
        quantity in 10_000..999_999 -> value + "K"
        quantity >= 1_000_000 && thousands < 100_000 -> value + "M"
        quantity >= 1_000_000 -> value + "." + thousandsToText + "M"
        else -> value
    }
    return result
}