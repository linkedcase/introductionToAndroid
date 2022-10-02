package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.activity.EditPostContentActivity
import ru.netology.nmedia.activity.PostContentActivity
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.impl.InMemoryPostRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(viewModel)

        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(
                intent, getString(R.string.chooser_share_post)
            )
            startActivity(shareIntent)
        }

        viewModel.playVideoLink.observe(this) { video ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, video)
                type = "video/mpeg"
            }

            val playIntent = Intent.createChooser(
                intent, getString(R.string.play)
            )
            startActivity(playIntent)
        }

        // Регистрируемся на результат выполнения активити  в этой лямбде получим результат выпонения нашей активити
        val postContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ) { postContent: String? ->
            postContent?.let(viewModel::onSaveButtonClicked) // метод onSaveButtonClicked вызовется с postContent только в том случае, если postContent not Null
        }

        val editPostContentActivityLauncher = registerForActivityResult(
            EditPostContentActivity.ResultContract
        ) { postContent: String? ->
            postContent?.let(viewModel::onSaveButtonClicked)
        }

        binding.fab.setOnClickListener {
            postContentActivityLauncher.launch(Unit)
        }

        viewModel.currentPost.observe(this) { currentPost ->
            if (currentPost?.textPost.isNullOrBlank()) {
                return@observe
            } else editPostContentActivityLauncher.launch(Unit)
        }
    }
}
