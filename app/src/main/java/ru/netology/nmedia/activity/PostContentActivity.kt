package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.PostContentActivityBinding

class PostContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = PostContentActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClicked(binding.edit.text?.toString())
        }
    }

    object ResultContract : ActivityResultContract<Unit, String?>() {
        override fun createIntent(context: Context, input: Unit): Intent =
            Intent(context, PostContentActivity::class.java) // Сформирровали явный интент для запуска активити

        override fun parseResult(resultCode: Int, intent: Intent?): String? {
            if (resultCode != Activity.RESULT_OK) return null
            intent ?: return null

            return intent.getStringExtra(RESULT_KEY)
        }
    }

    private companion object {
        private const val RESULT_KEY = "postNewContent"
    }

    private fun onOkButtonClicked(postContent: String?) {
        if (postContent.isNullOrBlank()) {
            setResult(Activity.RESULT_CANCELED, intent) // Сообщаем результат работы активити. В данном случае он неудачный
        } else {
            val resultIntent = Intent() // Результирующий интент
            resultIntent.putExtra(RESULT_KEY, postContent) // положили в интент текст ,который ввел пользователь на экране
            setResult(Activity.RESULT_OK, resultIntent) //здесь результат работы положительный и в интенте уже находится тект ,который ввел пользователь на экране
        }
        finish()
    }
}

