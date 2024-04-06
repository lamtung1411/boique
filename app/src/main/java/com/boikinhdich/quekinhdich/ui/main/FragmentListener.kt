package com.boikinhdich.quekinhdich.ui.main

import com.boikinhdich.quekinhdich.adapter.CardModel

interface FragmentListener {
    fun onSelectQueFragment()
    fun onDetailQueFragment(item: CardModel)
    fun onTermsFragment()
    fun onTutorialQueFragment()
}