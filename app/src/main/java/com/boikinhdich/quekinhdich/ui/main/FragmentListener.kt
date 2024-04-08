package com.boikinhdich.quekinhdich.ui.main

import com.boikinhdich.quekinhdich.adapter.CardModel

interface FragmentListener {
    fun onDetailQueFragment(item: CardModel)
    fun onTutorialQueFragment()
    fun onDialogSetting()
}