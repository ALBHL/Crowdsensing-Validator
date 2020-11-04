package com.example.validator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_image.*
import kotlinx.android.synthetic.main.activity_validate_image.*

class ValidateImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validate_image)

        val images = intent.getStringExtra(InboxActivity.USER_KEY)
        val context = this
        val db = DataBaseHandler(context)
        val data = db.readData()

        recyclerViewContents.layoutManager = LinearLayoutManager(this)
        recyclerViewContents.adapter = images?.let { ValidateImageAdapter(it) }

        ButtonValidation.setOnClickListener {
            val intent = Intent(this, OutboxActivity::class.java)
            startActivity(intent)
        }
    }
}