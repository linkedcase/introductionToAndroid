package ru.netology.nmedia

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.render(post)

        binding.root.setOnClickListener{
            binding.root
        }

        binding.avatar.setOnClickListener {
            binding.avatar
        }

        binding.likes.setOnClickListener {
            post.likedByMe = !post.likedByMe
            if (post.likedByMe) post.likes++ else post.likes--
            binding.likes.setImageResource(getLikeIconResId(post.likedByMe))
            binding.sumLikes.text = printQuantity(post.likes)
        }

        binding.share.setOnClickListener{
            post.share++
            binding.sumShare.text = printQuantity(post.share)
        }

        binding.views.setOnClickListener{
            post.views++
            binding.sumViews.text = printQuantity(post.views)
        }
    }
}