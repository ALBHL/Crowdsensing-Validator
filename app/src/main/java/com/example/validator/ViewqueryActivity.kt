package com.example.validator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_viewquery.*
import kotlinx.android.synthetic.main.outbox_file_row.*

class ViewqueryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewquery)

        val context = this
        val db = DataBaseHandler(context)

        btnInsert.setOnClickListener {
            if (editTextName.text.toString().isNotEmpty() &&
                editTextAge.text.toString().isNotEmpty()) {
                val user = User(editTextName.text.toString(), editTextAge.text.toString().toInt())
                db.insertData(user)
            } else {
                Toast.makeText(context, "Please fill all datas", Toast.LENGTH_SHORT).show()
            }
        }


        btn_read.setOnClickListener {
            val data = db.readData()
            tvResult.text = ""
            for (i in 0 until data.size) {
                tvResult.append(data[i].id.toString() + " " + data[i].name + " " + data[i].age + "\n")
            }
        }


        btn_update.setOnClickListener {
            db.updateData()
            btn_read.performClick()
        }


        btn_delete.setOnClickListener {
            db.deleteData()
            btn_read.performClick()
        }
    }
}