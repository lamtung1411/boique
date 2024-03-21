package com.boikinhdich.quekinhdich.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.adapter.CardAdapter
import com.boikinhdich.quekinhdich.adapter.CardModel
import com.boikinhdich.quekinhdich.adapter.ContentAdapter

class DetailQueFragment : Fragment() {

    private lateinit var contentRecyclerView: RecyclerView
    private lateinit var contentAdapter: ContentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_que, container, false)
        val item = arguments?.getSerializable("item") as CardModel
        val idImageSmall = resources.getIdentifier("ic_${item.id}", "drawable", requireActivity().packageName)

        val tvIdQue = view.findViewById<TextView>(R.id.tvIdQue)
        val tvLoaiQue = view.findViewById<TextView>(R.id.tvLoaiQue)
        val tvTenQue = view.findViewById<TextView>(R.id.tvTenQue)

        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val imgSmall = view.findViewById<ImageView>(R.id.imgSmall)
//        val imgDescription = view.findViewById<ImageView>(R.id.imgDescription)

        tvIdQue.text = item.id.toString()
        tvLoaiQue.text = item.type
        tvTenQue.text = item.title
        tvDescription.text = item.description
        imgSmall.setImageResource(idImageSmall)

        val items = item.content.split("\n")

        contentRecyclerView = view.findViewById(R.id.recyclerView)
        contentAdapter = ContentAdapter()
        contentRecyclerView.layoutManager = LinearLayoutManager(context)
        contentRecyclerView.setHasFixedSize(true)

        contentRecyclerView.adapter = contentAdapter
        contentAdapter.setItems(items)


        return view
    }
}