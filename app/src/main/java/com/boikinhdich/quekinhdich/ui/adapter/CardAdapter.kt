package com.boikinhdich.quekinhdich.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.adapter.CardModel


class CardAdapter : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    private var items = listOf<CardModel>()

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    fun setItems(items: List<CardModel>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageCard)
//        val imageView: TextView = itemView.findViewById(R.id.imageCard)
    }


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.imageView.setOnClickListener {
            itemClickListener?.onItemClick(position)
        }

//        holder.imageView.text = items[position].id.toString()

        // sets the image to the imageview from our itemHolder class
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return items.size
    }
}
