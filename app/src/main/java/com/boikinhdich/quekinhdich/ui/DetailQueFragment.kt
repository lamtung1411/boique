package com.boikinhdich.quekinhdich.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.greenlight.utils.VNCharacterUtils
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.ui.adapter.CardAdapter
import com.boikinhdich.quekinhdich.adapter.CardModel
import com.boikinhdich.quekinhdich.ui.adapter.ContentAdapter
import com.boikinhdich.quekinhdich.databinding.FragmentDetailQueBinding
import com.boikinhdich.quekinhdich.databinding.FragmentSelectQueBinding
import com.boikinhdich.quekinhdich.ui.main.FragmentListener

class DetailQueFragment : Fragment() {

    private var item: CardModel? = null

    companion object {
        fun newInstance(item: CardModel) =
            DetailQueFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("item", item)
                }
            }
    }

    private var _binding: FragmentDetailQueBinding? = null
    private val binding get() = _binding!!

    private val contentAdapter by lazy { ContentAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments?.getSerializable("item") as CardModel
    }

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
            val idImageSmall =
                resources.getIdentifier(
                    "ic_${item!!.id}",
                    "drawable",
                    requireActivity().packageName
                )

            val title = item!!.title.toLowerCase()
            val nameDescription = VNCharacterUtils.removeAccent(title).lowercase()
            val nameDescriptionCorrect = nameDescription.replace(" ", "_")
            val idImageDescription = resources.getIdentifier(
                "ic_${item!!.id}_${nameDescriptionCorrect}",
                "drawable",
                requireActivity().packageName
            )

            btnBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }

            tvIdQue.text = item!!.id.toString()
            tvLoaiQue.text = item!!.type
            Log.e("tvLoaiQue", tvLoaiQue.toString())
            if (tvLoaiQue.text == "Quẻ\nCát" || tvLoaiQue.text == "Bình\nHòa" || tvLoaiQue.text == "Quẻ\nCát\nHanh") {
                tvLoaiQue.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                tvLoaiQue.setBackgroundResource(R.drawable.ic_khung_red)
            } else {
                tvLoaiQue.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                tvLoaiQue.setBackgroundResource(R.drawable.ic_khung)
            }

            tvTenQue.text = item!!.title
            tvDescription.text = item!!.description

            imgSmall.setImageResource(idImageSmall)
            imgDescription.setImageResource(idImageDescription)

            val items = item!!.content.split("\n")
            contentAdapter.setItems(items)

            contentRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = contentAdapter
            }
        }
    }


}