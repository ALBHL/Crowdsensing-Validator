package com.example.validator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_image.*
import kotlinx.android.synthetic.main.activity_validate_image.*
import kotlinx.android.synthetic.main.activity_viewquery.*

class ValidateImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validate_image)

        val images = intent.getStringExtra(InboxActivity.USER_KEY)
        val cur_id = intent.getStringExtra(InboxActivity.ROW_ID)
        val context = this
        val db = DataBaseHandler(context)
        val data = db.readInData()

        textview_metadata.text = ""
        for (i in 0 until data.size) {
            textview_metadata.append(data[i].id.toString() + " " + data[i].name + " " + data[i].age + data[i].imageurl + "\n")
        }

        recyclerViewContents.layoutManager = LinearLayoutManager(this)
        recyclerViewContents.adapter = images?.let { ValidateImageAdapter(it) }

        buttonDisapprove.setOnClickListener {
            db.deleteInRow(cur_id.toString())
            d("hey?", cur_id.toString())
            val intent = Intent(this, SuccessValidateActivity::class.java)
            startActivity(intent)
        }

        ButtonValidation.setOnClickListener {
            val user = User("Take a picture of ** park", 1,
                "https://static.stalbert.ca/site/assets/files/5087/lions-park_header_2019.-full.jpg https://www.oakville.ca/images/1140-park-albion.jpg")
            db.insertData(user)
            db.deleteInRow(cur_id.toString())
            val intent = Intent(this, SuccessValidateActivity::class.java)
            startActivity(intent)
        }
    }
}