package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.Post
import ru.netology.nmedia.databinding.EditPostContentActivityBinding
import ru.netology.nmedia.viewModel.PostViewModel

class EditPostContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = EditPostContentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val textPost = currentPost?.textPost
                setText(textPost)
                binding.group.visibility = View.GONE
                if (textPost != null) {
                    requestFocus()
                    binding.group.visibility = View.VISIBLE
                }
            }
        }

        binding.editCancel.setOnClickListener {
            val editedText = viewModel.onEditCancelClicked()
            binding.contentEditText.setText(editedText)
        }

        binding.saveButton.setOnClickListener {
            onSaveEditClicked(binding.contentEditText.text?.toString())
        }
    }

    private fun onSaveEditClicked(postContent: String?) {
        if (postContent.isNullOrBlank()) {
            setResult(Activity.RESULT_CANCELED, intent)
        } else {
            val resultIntent = Intent()
            resultIntent.putExtra(EDIT_POST_CONTENT_KEY, postContent)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }

    private companion object {
        private const val EDIT_POST_CONTENT_KEY = "editedPostContent"
    }

    object ResultContract : ActivityResultContract<Unit, String?>() {

        override fun createIntent(context: Context, input: Unit): Intent =
            Intent(context, EditPostContentActivity::class.java) // Create new intent

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode != Activity.RESULT_OK) return null
            intent ?: return null

            return intent.getStringExtra(EDIT_POST_CONTENT_KEY)
        }
    }
}