package com.example.intentsjustcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.isDigitsOnly
import com.example.intentsjustcode.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendTextBtn.setOnClickListener {
            val text = binding.input.text.toString()
            validateInput(text) {
                useExplicitIntent(it)
            }
        }

        binding.sayHelloBtn.setOnClickListener {
            val text = binding.input.text.toString()
            validateInput(text) {
                useImplicitIntent(it)
            }
        }

    }

    private fun validateInput(text: String, sendSeconds: (Long) -> Unit = {}) {
        when {
            text.isBlank() -> binding.input.error = "must not be blank"
            text.startsWith("0") -> binding.input.error = "seconds can not start with 0"
            text.isDigitsOnly().not() -> binding.input.error = "must be a number"
            else -> {
                sendSeconds(text.toLong())
            }
        }
    }

    private fun useImplicitIntent(seconds: Long) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(MyEnum.seconds.name, seconds)
        intent.type = "text/plain"
        startActivity(intent)
    }

    private fun useExplicitIntent(seconds: Long) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(MyEnum.seconds.name, seconds)
        startActivity(intent)
    }
}