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
        val cur_name = intent.getStringExtra(InboxActivity.ROW_NAME)
        val context = this
        val db = DataBaseHandler(context)
        val data = db.readInData()

        textview_metadata.text = ""
        textview_metadata.text = "Inbox\n"
        for (i in 0 until data.size) {
            textview_metadata.append(data[i].id.toString() + " " + data[i].name + " " + data[i].age + data[i].imageurl +
                    "READY: " + data[i].ready + "COLLECT: " + data[i].collect + "VALIDATE: " + data[i].validate + "\n")
        }

        recyclerViewContents.layoutManager = LinearLayoutManager(this)
        recyclerViewContents.adapter = images?.let { ValidateImageAdapter(it) }

        buttonDisapprove.setOnClickListener {
            if (cur_name != null) {
                db.updateInRow(cur_name)
            }
            val intent = Intent(this, SuccessValidateActivity::class.java)
            startActivity(intent)
        }

        ButtonValidation.setOnClickListener {
            var user = User()
            if (cur_name != null && images != null) {
                user = User(cur_name, 1, images)
                db.insertData(user)
            }
            if (cur_name != null) {
                db.deleteInRow(cur_name)
            }
            val intent = Intent(this, SuccessValidateActivity::class.java)
            startActivity(intent)
        }
    }
}