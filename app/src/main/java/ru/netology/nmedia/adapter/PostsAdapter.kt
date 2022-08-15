package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostListItemBinding
import ru.netology.nmedia.databinding.PostListItemBinding.inflate
import ru.netology.nmedia.impl.getLikeIconResId
import ru.netology.nmedia.impl.printQuantity

class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostListItemBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, interactionListener)
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
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)// говорим, что будет раздуватся меню options_post
                setOnMenuItemClickListener {menuItem->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.likes.setOnClickListener { listener.onLikeClicked(post) }
            binding.share.setOnClickListener { listener.onShareClicked(post) }
            binding.views.setOnClickListener { listener.onViewClicked(post) }
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
                options.setOnClickListener { popupMenu.show() }
            }
        }
    }
}