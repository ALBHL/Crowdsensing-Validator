package com.example.validator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_viewquery.*

class ViewqueryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewquery)

        val context = this
        val db = DataBaseHandler(context)

        btnInsert.setOnClickListener {
            if (editTextName.text.toString().isNotEmpty() &&
                editTextAge.text.toString().isNotEmpty()) {
                var user = User(editTextName.text.toString(), editTextAge.text.toString().toInt(),
                    "https://static.stalbert.ca/site/assets/files/5087/lions-park_header_2019.-full.jpg https://www.oakville.ca/images/1140-park-albion.jpg")
                db.insertInData(user)
                user = User("Take a picture of ** park", 1,
                    "https://static.stalbert.ca/site/assets/files/5087/lions-park_header_2019.-full.jpg https://www.oakville.ca/images/1140-park-albion.jpg")
                db.insertData(user)
                user = User("Take a picture of ** pub", 1,
                    "https://www.ctvnews.ca/polopoly_fs/1.5023024.1594692616!/httpImage/image.jpg_gen/derivatives/landscape_620/image.jpg https://ichef.bbci.co.uk/news/976/cpsprodpb/6D30/production/_111325972_gettyimages-1148223648.jpg https://www.thespiritsbusiness.com/content/http://www.thespiritsbusiness.com/media/2020/03/Spirits-Bottles.jpg")
                db.insertData(user)
                user = User("Description", 1, "https://miro.medium.com/max/4064/1*qYUvh-EtES8dtgKiBRiLsA.png")
                db.insertInData(user)
                user = User("Information", 1, "https://9to5mac.com/wp-content/uploads/sites/6/2019/03/mac.jpg?quality=82&strip=all https://cdn.cultofmac.com/wp-content/uploads/2014/04/13890505914_ff1aeabb18_b-640x480.jpg")
                db.insertInData(user)
            } else {
                Toast.makeText(context, "Please fill all datas", Toast.LENGTH_SHORT).show()
            }
        }


        btn_read.setOnClickListener {
            val data = db.readInData()
            val data2 = db.readData()
            tvResult.text = "Inbox\n"
            for (i in 0 until data.size) {
                tvResult.append(data[i].id.toString() + " " + data[i].name + " " + data[i].age + data[i].imageurl +
                        "READY: " + data[i].ready + "COLLECT: " + data[i].collect + "VALIDATE: " + data[i].validate + "\n")
            }
            tvResult.append("\nOutbox\n")
            for (i in 0 until data2.size) {
                tvResult.append(data2[i].id.toString() + " " + data2[i].name + " " + data2[i].age + data2[i].imageurl +
                        "READY: " + data2[i].ready + "COLLECT: " + data2[i].collect + "VALIDATE: " + data2[i].validate + "\n")
            }
        }

        btn_update.setOnClickListener {
            db.updateData()
            btn_read.performClick()
        }

        btn_delete.setOnClickListener {
            db.deleteInRow("7")
            btn_read.performClick()
        }
    }
}
