package com.example.validator

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.collector.User
import com.google.firebase.auth.FirebaseAuth
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_inbox.*


class InboxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        supportActionBar?.title = "Inbox"

        //verifyUserIsLoggedIn()

        button_inbox.setOnClickListener {
            val intent = Intent(this, ViewqueryActivity::class.java)
            startActivity(intent)
        }

        deleteBox.setOnClickListener {
//            val intent = Intent(this, DeleteActivity::class.java)
//            startActivity(intent)
            val resolver: ContentResolver = contentResolver
            resolver.delete(AcronymProvider.CONTENT_URI, null, null)
        }

        fetch_inbox.setOnClickListener {
            val cursor: Cursor? = contentResolver.query(
                Uri.parse("content://com.example.collector/AcronymProvider/CPInbox"),
                null,
                null,
                null,
                null
            )

            Log.d("cursor", cursor.toString())
            if (cursor!!.moveToFirst()) {
                val strBuild = StringBuilder()
                while (!cursor.isAfterLast) {
                    strBuild.append(
                        cursor.getString(cursor.getColumnIndex(COL_TASK_ID)) + " " +
                                cursor.getString(cursor.getColumnIndex(COL_NAME)) + " " +
                                cursor.getString(cursor.getColumnIndex(COL_TASK_DESCRIPTION)) + " " +
                                cursor.getString(cursor.getColumnIndex(COL_COUNT)) + " " +
                                cursor.getString(cursor.getColumnIndex(COL_TEXT)) + " " +
                                cursor.getString(cursor.getColumnIndex(COL_PROFILE)) + "\n"
                    )
                    cursor!!.moveToNext()
                }
                Log.d("asdsa", strBuild.toString())
            }
        }

        val context = this
        val db = DataBaseHandler(context)
        val data = db.readInData()

        val cursor: Cursor? = contentResolver.query(
            Uri.parse("content://com.example.collector/AcronymProvider/CPInbox"),
            null,
            null,
            null,
            null
        )

        val list = ArrayList<User>()
        Log.d("cursor", cursor.toString())
        if (cursor!!.moveToFirst()) {
            val strBuild = StringBuilder()
            while (!cursor.isAfterLast) {
                list.add(User(
                    cursor.getString(cursor.getColumnIndex(COL_PROFILE)),
                    cursor.getString(cursor.getColumnIndex(COL_TASK_ID)),
                    cursor.getString(cursor.getColumnIndex(COL_NAME)),
                    cursor.getString(cursor.getColumnIndex(COL_TASK_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(COL_PROFILE)),
                    cursor.getInt(cursor.getColumnIndex(COL_COUNT)),
                    cursor.getString(cursor.getColumnIndex(COL_TEXT))
                ))
                cursor!!.moveToNext()
            }
            Log.d("asdsa", strBuild.toString())
        }

        fetchUsers(list)

    }

    companion object {
        val USER_KEY = "USER_KEY"
        val ROW_ID = "ROW_ID"
        val ROW_NAME = "ROW_NAME"
    }

    private fun fetchUsers(data: MutableList<User>) {
        val adapter = GroupAdapter<ViewHolder>()
        for (i in 0 until data.size) {
            if (data[i].validate == "false") {
                adapter.add(UserItem(data[i]))
            }
        }
        adapter.setOnItemClickListener { item, view ->
            val userItem = item as UserItem
            val intent = Intent(view.context, ValidateImageActivity::class.java)
            intent.putExtra(USER_KEY, userItem.user.imageurl)
            intent.putExtra(ROW_ID, userItem.user.id)
            intent.putExtra(ROW_NAME, userItem.user.task_name)
            startActivity(intent)
        }
        recycleview_inbox.adapter = adapter
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)    // clear off the back stack
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
