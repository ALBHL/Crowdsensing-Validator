package com.example.validator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.images_row.view.*


class ShowImageAdapter: RecyclerView.Adapter<CustomViewHolder>() {
    val videoTitles = listOf<String>("Fisrt", "Second", "Third")

    // number of items
    override fun getItemCount(): Int {
        return 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.images_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val videoTitle = videoTitles.get(position)
        holder.view.textViewImageTitle.text = videoTitle
    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {

}