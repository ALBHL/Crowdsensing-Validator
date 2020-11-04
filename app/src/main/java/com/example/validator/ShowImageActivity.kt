package com.example.validator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_image.*

class ShowImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)

        val context = this
        val db = DataBaseHandler(context)
        val data = db.readData()
//        textViewdbResult.text = ""
//        for (i in 0 until data.size) {
//            textViewdbResult.append(data[i].id.toString() + " " + data[i].name + " " + data[i].age + "\n")
//        }
        val images = data[0].imageurl
//        Picasso.get().load(data[0].imageurl).into(imageViewdbRes)
        recyclerViewDetails.layoutManager = LinearLayoutManager(this)
        recyclerViewDetails.adapter = ShowImageAdapter(images)

        ButtonValidation.setOnClickListener {
            val intent = Intent(this, OutboxActivity::class.java)
            startActivity(intent)
        }
    }
}

class HomeFeed {

}