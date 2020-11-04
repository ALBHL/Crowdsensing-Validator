package com.example.validator

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
//        Picasso.get().load(data[0].imageurl).into(imageViewdbRes)
        recyclerViewDetails.layoutManager = LinearLayoutManager(this)
        recyclerViewDetails.adapter = ShowImageAdapter()

    }
}