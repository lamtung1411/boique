package com.boikinhdich.quekinhdich.ui

import android.os.Bundle
import android.util.Log
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
import com.android.greenlight.utils.VNCharacterUtils
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.adapter.CardAdapter
import com.boikinhdich.quekinhdich.adapter.CardModel
import com.boikinhdich.quekinhdich.adapter.ContentAdapter
import com.boikinhdich.quekinhdich.databinding.FragmentDetailQueBinding
import com.boikinhdich.quekinhdich.databinding.FragmentSelectQueBinding

class DetailQueFragment : Fragment() {

    private var _binding: FragmentDetailQueBinding? = null
    private val binding get() = _binding!!

    private val contentAdapter by lazy { ContentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailQueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val item = arguments?.getSerializable("item") as CardModel
            val idImageSmall =
                resources.getIdentifier("ic_${item.id}", "drawable", requireActivity().packageName)

            val title = item.title.toLowerCase()
            val nameDescription = VNCharacterUtils.removeAccent(title).lowercase()
            val nameDescriptionCorrect = nameDescription.replace(" ", "_")
            val idImageDescription = resources.getIdentifier(
                "ic_${item.id}_${nameDescriptionCorrect}",
                "drawable",
                requireActivity().packageName
            )

//        Log.e("VNCharacterUtils ","${VNCharacterUtils.removeAccent("${idImageDescription}").lowercase()}")
            Log.e("nameDescriptionCorrect ", nameDescriptionCorrect)
//        Log.e("originalString ", originalString)

            btnBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            tvIdQue.text = item.id.toString()
            tvLoaiQue.text = item.type
            tvTenQue.text = item.title
            tvDescription.text = item.description

            imgSmall.setImageResource(idImageSmall)
            imgDescription.setImageResource(idImageDescription)

            val items = item.content.split("\n")

            contentAdapter.setItems(items)

            contentRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = contentAdapter
            }
        }
    }
}