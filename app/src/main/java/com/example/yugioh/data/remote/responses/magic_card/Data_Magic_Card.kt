package com.example.yugioh.data.remote.responses.magic_card

import com.example.yugioh.data.remote.responses.universal.CardImage
import com.example.yugioh.data.remote.responses.universal.CardPrice
import com.example.yugioh.data.remote.responses.universal.CardSet

data class Data_Magic_Card(
    val archetype: String,
    val card_images: List<CardImage>,
    val card_prices: List<CardPrice>,
    val card_sets: List<CardSet>,
    val desc: String,
    val id: Int,
    val name: String,
    val race: String,
    val type: String
)