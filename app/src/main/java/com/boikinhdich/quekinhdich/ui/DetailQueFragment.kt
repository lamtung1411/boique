package com.boikinhdich.quekinhdich.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.adapter.CardModel

class DetailQueFragment : Fragment() {

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
        val tvInformationQue = view.findViewById<TextView>(R.id.tvInformationQue)
        val imgSmall = view.findViewById<ImageView>(R.id.imgSmall)
        val imgDescription = view.findViewById<ImageView>(R.id.imgDescription)

        tvIdQue.text = item.id.toString()
        tvLoaiQue.text = item.type
        tvTenQue.text = item.title
        tvDescription.text = item.description
        tvInformationQue.text = item.content
        imgSmall.setImageResource(idImageSmall)


        return view
    }
}