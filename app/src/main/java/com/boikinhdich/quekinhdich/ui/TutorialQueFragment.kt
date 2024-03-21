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

class TutorialQueFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutorial_que, container, false)


        return view
    }
}