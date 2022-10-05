package ru.netology.nmedia.impl

import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository

object InMemoryPostRepository : PostRepository {

    private const val GENERATED_POSTS_AMOUNT = 100

    private var nextIdPost = GENERATED_POSTS_AMOUNT.toLong()

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
//        set(value) {
//            data.value = value
//        }

    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                idPost = index + 1L,
                authorPost = "Нетология. Университет интернет-профессий будущего",
                textPost = "Текст поста № ${index + 1}",
                datePost = "03 октября в 17:38",
                likes = 999,
                share = 90,
                views = 9,
                likedByMe = false,
                videoLink = if (index % 2 == 0) "https://www.youtube.com/watch?v=WhWc3b3KhnY" else ""
            )
        }
    )

    override fun like(idPost: Long) {

        data.value = posts.map { post ->
            if (idPost == post.idPost) post.copy(
                likedByMe = !post.likedByMe,
                likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
            ) else post
        }
    }

    override fun share(idPost: Long) {
        data.value = posts.map { post ->
            if (idPost == post.idPost) post.copy(share = post.share + 1) else post
        }
    }

    override fun view(idPost: Long) {

        data.value = posts.map { post ->
            if (idPost == post.idPost) post.copy(views = post.views + 1) else post
        }
    }

    override fun delete(idPost: Long) {
        data.value = posts.filter { post ->
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

override fun getById(idPost: Long): Post? {
    return posts.find { it.idPost == idPost }
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