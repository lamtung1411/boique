package com.boikinhdich.quekinhdich.adapter

import java.io.Serializable

data class CardModel(
    val id: Int,
    val title: String,
    val type: String,
    val description: String,
    val content: String,
    var imageDescription: String
    ): Serializable