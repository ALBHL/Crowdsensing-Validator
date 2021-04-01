package com.example.validator

import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_validate_image.*

class ValidateImageActivity : AppCompatActivity() {
    val PROVIDER_NAME = "com.example.collector/AcronymProvider"
    val URL = "content://$PROVIDER_NAME/Inbox"
    val CONTENT_URI = Uri.parse(URL)

    val TABLEIN_NAME="Inbox"
    val COL_NAME = "name"
    val COL_AGE = "age"
    val COL_ID = "id"
    val COL_URL = "imageurl"
    val COL_PROFILE = "profileimg"
    val COL_STAGE = "current_stage"
    val COL_IMAGE_BIT = "picturetaken"
    val COL_MODEL = "inf_model"
    val COL_ITEM = "item_of_interest"
    val COL_COUNT = "item_count"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validate_image)

        val images = intent.getStringExtra(InboxActivity.USER_KEY)
        val cur_id = intent.getStringExtra(InboxActivity.ROW_ID)
        val cur_name = intent.getStringExtra(InboxActivity.ROW_NAME)
        val context = this
//        val db = DataBaseHandler(context)
//        val data = db.readData()

        val result = contentResolver.query(CONTENT_URI,
            arrayOf(COL_ID, COL_NAME, COL_STAGE, COL_AGE, COL_PROFILE, COL_URL, COL_IMAGE_BIT, COL_MODEL, COL_ITEM, COL_COUNT),
            null, null, null)
        val data = result?.let { readInData(cur_id, it) }
        lateinit var bitmp: Bitmap
        var inf_ret = ""

        textview_metadata.text = ""
        textview_metadata.text = "Inbox\n"
        if (data != null) {
            for (i in 0 until data.size) {
                textview_metadata.append(data[i].id.toString() + " " + data[i].name + " " + data[i].age + data[i].imageurl +
                        "STAGE: " + data[i].cur_stage + "\n")
                bitmp = data[i].bmp!!
                inf_ret = data[i].item
            }
        }

        recyclerViewContents.layoutManager = LinearLayoutManager(this)
        recyclerViewContents.adapter = images?.let { ValidateImageAdapter(it, bitmp, inf_ret) }

        buttonDisapprove.setOnClickListener {
//            if (cur_id != null) {
//                db.updateRow(cur_id, "deleted")
//            }
//            val intent = Intent(this, SuccessValidateActivity::class.java)
//            startActivity(intent)
        }

        ButtonValidation.setOnClickListener {
//            Toast.makeText(context,cur_id, Toast.LENGTH_SHORT).show()
//            if (cur_id != null) {
//                db.updateRow(cur_id, "validated")
//            }
//            val intent = Intent(this, SuccessValidateActivity::class.java)
//            startActivity(intent)
        }
    }

    private fun readInData(cur_id: String?, result: Cursor) : MutableList<User> {
        val list : MutableList<User> = ArrayList()
        while (result.moveToNext()) {
            if (result.getString(0) == cur_id) {
                val user = User()
                val retblob = result.getBlob(result.getColumnIndex(COL_IMAGE_BIT))
                user.id = result.getString(0).toInt()
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                user.age = result.getString(result.getColumnIndex(COL_AGE)).toInt()
                user.imageurl = result.getString(5)
                user.cur_stage = result.getString(2)
                user.profileurl = result.getString(4)
                user.bmp = retblob?.size?.let { BitmapFactory.decodeByteArray(retblob, 0, it) }
                user.item = result.getString(8)
                list.add(user)
            }
        }
        return list
    }

//    private fun readDataImg(cur_id: String, result: Cursor) : Bitmap? {
//        var retblob: ByteArray? = null
//        while (result.moveToFirst()) {
//            if (result.getString(0) == "1") {
//                retblob = result.getBlob(result.getColumnIndex(COL_IMAGE_BIT))
//            }
//        }
//        return retblob?.size?.let { BitmapFactory.decodeByteArray(retblob, 0, it) }
//    }
}
