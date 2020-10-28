package com.example.validator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_outbox.*
import kotlinx.android.synthetic.main.outbox_file_row.*

class OutboxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_outbox)

        supportActionBar?.title = "Outbox"

        val adapter = GroupAdapter<ViewHolder>()
        adapter.add(UserItem())
        adapter.add(UserItem())
        adapter.add(UserItem())
        adapter.add(UserItem())
        adapter.add(UserItem())
        adapter.add(UserItem())
        recycleview_outbox.adapter = adapter

        fetchUsers()
    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().reference
        d("Outbox", "$ref")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(datasnap: DataSnapshot) {
                d("Outbox", "$ref")
                datasnap.children.forEach {
                    d("Outbox", it.toString())
                }
//                val adapter = GroupAdapter<ViewHolder>()
//                p0.children.forEach {
//                    Log.d("NewMessage", it.toString())
//                    val user = it.getValue(User::class.java)
//                    if (user != null) {
//                        adapter.add(UserItem(user))
//                    }
//                }
//                recyclerview_newmessage.adapter = adapter
            }

            override fun onCancelled(datasnap: DatabaseError) {
                d("Outbox", "cancelled")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_new_message -> {
                val intent = Intent(this, InboxActivity::class.java)
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
        menuInflater.inflate(R.menu.nav_menu_outbox, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

class UserItem(): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        // will be called in the list of user object
//        viewHolder.itemView.username_edittext_reg.text = user.username
//        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageview_new_message)
    }

    override fun getLayout(): Int {
        return R.layout.outbox_file_row
    }
}

// this is super tedious
//class CustomAdapter: RecyclerView.Adapter<ViewHolder> {
//  override fun onBindViewHolder(p0:, p1: Int) {
//    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//  }
//}