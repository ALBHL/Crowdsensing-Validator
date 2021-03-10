package com.example.validator

import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_inbox.*

class InboxActivity : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        supportActionBar?.title = "Inbox"

        verifyUserIsLoggedIn()

        button_inbox.setOnClickListener{
            val intent = Intent(this, ViewqueryActivity::class.java)
            startActivity(intent)
        }

        deleteBox.setOnClickListener{
            val intent = Intent(this, DeleteActivity::class.java)
            startActivity(intent)
        }

        button_takepic.setOnClickListener{
            val intent = Intent(this, CollectorActivity::class.java)
            startActivity(intent)
        }


        val context = this
        val db = DataBaseHandler(context)
//        val data = db.readData()

        val result = contentResolver.query(CONTENT_URI,
            arrayOf(COL_ID, COL_NAME, COL_STAGE, COL_AGE, COL_PROFILE, COL_URL, COL_IMAGE_BIT),
            null, null, null)
        val data = result?.let { readInData(it) }
        if (data != null) {
            fetchUsers(data)
        }


    }

    companion object {
        val USER_KEY = "USER_KEY"
        val ROW_ID = "ROW_ID"
        val ROW_NAME = "ROW_NAME"
    }

    private fun readInData(result: Cursor) : MutableList<User> {
        val list : MutableList<User> = ArrayList()
        while (result.moveToNext()) {
            val user = User()
            user.id = result.getString(0).toInt()
            user.name = result.getString(1)
            user.age = result.getString(3).toInt()
            user.imageurl = result.getString(5)
            user.cur_stage = result.getString(2)
            user.profileurl = result.getString(4)
            list.add(user)
        }
        return list
    }


    private fun fetchUsers(data: MutableList<User>) {
        val adapter = GroupAdapter<ViewHolder>()
        for (i in 0 until data.size) {
            if (data[i].cur_stage == "ready to be validated") {
                adapter.add(UserItem(data[i]))
            }
        }
        adapter.setOnItemClickListener { item, view ->
            val userItem = item as UserItem
            val intent = Intent(view.context, ValidateImageActivity::class.java)
            intent.putExtra(USER_KEY, userItem.user.imageurl)
            intent.putExtra(ROW_ID, userItem.user.id.toString())
            intent.putExtra(ROW_NAME, userItem.user.name)
            startActivity(intent)
        }
        recycleview_inbox.adapter = adapter
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)    // clear off the back stack
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_new_message -> {
                val intent = Intent(this, OutboxActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
