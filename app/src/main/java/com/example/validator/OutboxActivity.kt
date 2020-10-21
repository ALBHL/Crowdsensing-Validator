package com.example.validator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
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