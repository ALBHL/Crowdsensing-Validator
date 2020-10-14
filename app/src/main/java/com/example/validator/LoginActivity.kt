package com.example.validator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_edittext_login.text.toString()
            val password = password_edittext_login.text.toString()

            Log.d("Login", "Attempt login with email/pw: $email/***")

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and/or password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{
                    if (!it.isSuccessful) return@addOnCompleteListener

                    // else successful
                    Log.d("Login", "Successfully create user with uid: ${it.result?.user?.uid}")
                    val intent = Intent(this, InOutboxActivity::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Log.d("Login", "Failed to log in: ${it.message}")
                    Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        back_to_register_textview.setOnClickListener{
            finish()
        }

    }

}