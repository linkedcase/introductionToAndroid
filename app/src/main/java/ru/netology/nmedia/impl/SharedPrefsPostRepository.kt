package ru.netology.nmedia.impl

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.data.PostRepository
import kotlin.properties.Delegates

class SharedPrefsPostRepository(
    application: Application
) : PostRepository {

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    ) // получили преференсы в репозитории

    private var nextIdPost by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREFS_KEY, newValue) }
    }

    private var posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
        set(value) {
            prefs.edit {
                val serializedPosts = Json.encodeToString(value)
                putString(POSTS_PREFS_KEY, serializedPosts)
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val serializedPosts = prefs.getString(POSTS_PREFS_KEY, null)
        val posts: List<Post> = if (serializedPosts != null) {
            Json.decodeFromString(serializedPosts)
        } else emptyList()
        data = MutableLiveData(posts)
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

    override fun getById(idPost: Long): Post? {
        TODO("Not yet implemented")
    }

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(idPost = ++nextIdPost)
        ) + posts
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.idPost == post.idPost) post else it
        }
    }

    private companion object {
        const val NEXT_ID_PREFS_KEY = "nextId"
        const val POSTS_PREFS_KEY = "posts"
    }
}