package com.boikinhdich.quekinhdich.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import carbon.widget.ImageView
import com.amuse.animalsounds.utils.ext.diaLogSetting
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.adapter.CardAdapter
import com.boikinhdich.quekinhdich.adapter.CardModel
import com.boikinhdich.quekinhdich.adapter.model.fromJsonArray
import com.boikinhdich.quekinhdich.databinding.FragmentSelectQueBinding
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.testing.FakeReviewManager
import java.util.Random

class SelectQueFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var detailQueFragment: DetailQueFragment
    private var reviewManager: ReviewManager? = null

    private val cardAdapter by lazy { CardAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSelectQueBinding.inflate(inflater, container, false)

        detailQueFragment = DetailQueFragment()
        mainActivity = activity as MainActivity
        reviewManager = FakeReviewManager(context)

        binding.apply {
            // Khởi tạo RecyclerView và adapter

            cardRecylerview.apply {
                layoutManager = GridLayoutManager(context, 8)
                setHasFixedSize(true)
                adapter = cardAdapter
            }

            val json = requireContext().assets.open("data.json").bufferedReader().use { it.readText() }
            val items = fromJsonArray(json, CardModel::class.java)

            cardAdapter.apply {
                setItems(items)
                setOnItemClickListener(object : CardAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        setItemDetailQue(items[position])
                    }
                })
            }

            btnPalmOff.setOnClickListener {
                items.shuffle()
                cardAdapter.notifyDataSetChanged()
                cardRecylerview.scheduleLayoutAnimation()
            }

            btnInstruct.setOnClickListener {
                mainActivity.switchFragment(TutorialQueFragment(), "TutorialQueFragment", true)
            }

            btnSetting.setOnClickListener {
                mainActivity.diaLogSetting(reviewManager!!)
            }

            btnRandomQue.setOnClickListener {
                val random = Random()
                val randomNumber = random.nextInt(64) + 1

                for (item in items) {
                    if (item.id == randomNumber)
                        setItemDetailQue(item)
                }
            }
        }

        return binding.root
    }

    private fun setItemDetailQue(item: CardModel) {
        val bundle = Bundle()
        bundle.putSerializable("item", item)

        detailQueFragment.arguments = bundle
        mainActivity.switchFragment(detailQueFragment, "DetailQueFragment", true)
    }
}