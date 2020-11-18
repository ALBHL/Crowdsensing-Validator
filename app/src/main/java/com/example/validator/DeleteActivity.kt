package com.example.validator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_inbox.*

class DeleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inbox)

        supportActionBar?.title = "Deleted items"

        verifyUserIsLoggedIn()

        button_inbox.setOnClickListener{
            val intent = Intent(this, ViewqueryActivity::class.java)
            startActivity(intent)
        }

        val context = this
        val db = DataBaseHandler(context)
        val data = db.readInData()

        fetchUsers(data)

    }

    companion object {
        val USER_KEY = "USER_KEY"
        val ROW_ID = "ROW_ID"
        val ROW_NAME = "ROW_NAME"
    }

    private fun fetchUsers(data: MutableList<User>) {
        val adapter = GroupAdapter<ViewHolder>()
        for (i in 0 until data.size) {
            if (data[i].validate == "true") {
                adapter.add(UserItem(data[i]))
            }
        }
        adapter.setOnItemClickListener { item, view ->
            val userItem = item as UserItem
            val intent = Intent(view.context, ValidateImageActivity::class.java)
            intent.putExtra(USER_KEY, userItem.user.imageurl)
            intent.putExtra(ROW_ID, userItem.user.id)
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