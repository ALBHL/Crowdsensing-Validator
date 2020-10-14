package com.example.validator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button.setOnClickListener {
            val email = email_edittext_reg.text.toString()
            val password = password_edittext_reg.text.toString()

            d("MainActivity", "Email is: " + email)
            d("MainActivity", "Password is: $password")
        }

        already_have_account_textView.setOnClickListener {
            d("MainActivity", "Try to show login activity")

            // launch the login page
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}