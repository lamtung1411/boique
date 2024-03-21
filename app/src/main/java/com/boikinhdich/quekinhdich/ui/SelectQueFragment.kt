package com.boikinhdich.quekinhdich.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boikinhdich.quekinhdich.R
import com.boikinhdich.quekinhdich.adapter.CardAdapter
import com.boikinhdich.quekinhdich.adapter.CardModel
import com.boikinhdich.quekinhdich.model.fromJsonArray
import java.util.Random

class SelectQueFragment : Fragment() {

    private lateinit var cardRecylerview: RecyclerView
    private lateinit var cardAdapter: CardAdapter
    lateinit var mainActivity: MainActivity
    lateinit var detailQueFragment: DetailQueFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_select_que, container, false)

        detailQueFragment = DetailQueFragment()
        mainActivity = activity as MainActivity
        // Khởi tạo RecyclerView và adapter
        cardRecylerview = view.findViewById(R.id.recyclerView)
        cardAdapter = CardAdapter()
        cardRecylerview.layoutManager = GridLayoutManager(context, 8)
        cardRecylerview.setHasFixedSize(true)

        val btnPalmOff = view.findViewById<TextView>(R.id.btnPalmOff)
        val btnSetting = view.findViewById<TextView>(R.id.btnSetting)
        val btnRandomQue = view.findViewById<TextView>(R.id.btnRandomQue)

        val json = requireContext().assets.open("data.json").bufferedReader().use { it.readText() }
        val items = fromJsonArray(json, CardModel::class.java)

        cardRecylerview.adapter = cardAdapter
        cardAdapter.setItems(items)
        cardAdapter.setOnItemClickListener(object : CardAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                setItemDetailQue(items[position])
            }
        })

        btnPalmOff.setOnClickListener {
            items.shuffle()
            cardAdapter.notifyDataSetChanged()
            cardRecylerview.scheduleLayoutAnimation()
        }

        btnSetting.setOnClickListener {
            mainActivity.switchFragment(TutorialQueFragment(), "TutorialQueFragment", true)
        }

        btnRandomQue.setOnClickListener {
            val random = Random()
            val randomNumber = random.nextInt(64) + 1

            for (item in items) {
                if (item.id == randomNumber)
                    setItemDetailQue(item)
            }
        }

        return view
    }

    private fun setItemDetailQue(item: CardModel) {
        val bundle = Bundle()
        bundle.putSerializable("item", item)

        detailQueFragment.arguments = bundle
        mainActivity.switchFragment(detailQueFragment, "DetailQueFragment", true)
    }
}