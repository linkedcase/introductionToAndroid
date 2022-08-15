package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyBoard

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(viewModel)

        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()

                viewModel.onSaveButtonClicked(content)

                clearFocus()
                hideKeyBoard()
            }
        }

        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val textPost = currentPost?.textPost
                setText(textPost)
                if (textPost != null) requestFocus()
            }
        }
    }
}
