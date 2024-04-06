package com.boikinhdich.quekinhdich.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boikinhdich.quekinhdich.R


class ContentAdapter : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    private var items = listOf<String>()

    fun setItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvInformationQue: TextView = itemView.findViewById(R.id.tvInformationQue)
        val tvTitleQue: TextView = itemView.findViewById(R.id.tvTitleQue)
    }


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_content, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val title = item.split(":")[0]
        val content = item.split(":")[1]

        holder.tvTitleQue.text = title
        holder.tvInformationQue.text = content
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return items.size
    }
}
