package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.getLikeIconResId
import ru.netology.nmedia.printQuantity

typealias OnLikeListener = (Post) -> Unit
typealias OnShareListener = (Post) -> Unit
typealias  OnViewListener = (Post)  -> Unit

class PostsAdapter(
    private val onLikeClicked: OnLikeListener,
    private val isShared: OnShareListener,
    private val isViewed: OnViewListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, onLikeClicked, isShared, isViewed)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.idPost == newItem.idPost

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }

    class ViewHolder(
        private val binding: PostListItemBinding,
        private val onLikeClicked: OnLikeListener,
        private val isShared: OnShareListener,
        private val isViewed: OnViewListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        init {
            binding.likes.setOnClickListener { onLikeClicked(post) }
            binding.share.setOnClickListener { isShared(post) }
            binding.views.setOnClickListener { isViewed(post) }
        }

        fun bind(post: Post) {
            this.post = post

            with(binding) {
                author.text = post.authorPost
                date.text = post.datePost
                content.text = post.textPost
                sumLikes.text = printQuantity(post.likes)
                sumShare.text = printQuantity(post.share)
                sumViews.text = printQuantity(post.views)
                likes.setImageResource(getLikeIconResId(post.likedByMe))
                share.setImageResource(R.drawable.ic_share_24dp)
                views.setImageResource(R.drawable.ic_eye_24dp)
            }
        }
    }
}