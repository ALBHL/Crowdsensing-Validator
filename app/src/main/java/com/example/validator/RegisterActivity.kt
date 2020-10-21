package com.example.validator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.util.Log.w
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register_button.setOnClickListener {
            performRegister()
        }

        already_have_account_textView.setOnClickListener {
            d("RegisterActivity", "Try to show login activity")

            // launch the login page
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performRegister() {
        val email = email_edittext_reg.text.toString()
        val password = password_edittext_reg.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            return
        }

        d("RegisterActivity", "Email is: " + email)
        d("RegisterActivity", "Password is: $password")

        // Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener

                // else successful
                d("RegisterActivity", "Successfully create user with uid: ${ it.result?.user?.uid }")
//                uploadDataToFirebaseStorage()
//                saveUserToFirebaseDatabase("https://google.com")
//                readTestFirebase()
            }
            .addOnFailureListener {
                d("RegisterActivity", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Failed to create user: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

//    private fun uploadDataToFirebaseStorage() {
//    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        d("RegisterActivity","UUID: $uid")
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        d("RegisterActivity","Ref: $ref")


        val user = User(uid, username_edittext_reg.text.toString(), profileImageUrl)

        ref.setValue("hello")
            .addOnCanceledListener {
                d("RegisterActivity", "cancelled")
            }
            .addOnSuccessListener {
                d("RegisterActivity", "Finally we saved the user to Firebase Database")
            }
            .addOnFailureListener {
                d("RegisterActivity", "Failed to set value to database: ${it.message}")
            }
    }

    private fun readTestFirebase() {
        val database = Firebase.database
        val myRef = database.reference
        d("RegisterActivity", "$myRef")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue<String>()
                d("RegisterActivity", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                w("RegisterActivity", "Failed to read value.", error.toException())
            }
        })
    }

}

class User(val uid: String, val username: String, val profileImageUrl: String)