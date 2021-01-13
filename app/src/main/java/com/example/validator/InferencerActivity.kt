package com.example.validator

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_inferencer.*


class InferencerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inferencer)

        val context = this
        val db = DataBaseHandler(context)
        val bmp = db.readDataImg()
        imageViewshow.setImageBitmap(bmp)
    }
}