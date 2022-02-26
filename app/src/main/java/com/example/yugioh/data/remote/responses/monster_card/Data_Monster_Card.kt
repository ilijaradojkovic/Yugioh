package com.example.yugioh.data.remote.responses.monster_card

import com.example.yugioh.data.remote.responses.universal.CardImage
import com.example.yugioh.data.remote.responses.universal.CardPrice
import com.example.yugioh.data.remote.responses.universal.CardSet

data class Data_Monster_Card(
    val archetype: String="",
    val atk: Int=0,
    val attribute: String="",
    val card_images: List<CardImage> = emptyList(),
    val card_prices: List<CardPrice> = emptyList(),
    val card_sets: List<CardSet> = emptyList(),
    val def: Int=0,
    val desc: String="",
    val id: Int=0,
    val level: Int=0,
    val name: String="",
    val race: String="",
    val type: String=""
)