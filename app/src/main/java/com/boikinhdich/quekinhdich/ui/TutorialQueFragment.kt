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
import com.boikinhdich.quekinhdich.databinding.FragmentSelectQueBinding
import com.boikinhdich.quekinhdich.databinding.FragmentTutorialQueBinding

class TutorialQueFragment : Fragment() {

    private var _binding: FragmentTutorialQueBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorialQueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

}