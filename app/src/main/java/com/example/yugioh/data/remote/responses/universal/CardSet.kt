package com.example.yugioh.data.remote.responses.universal

data class CardSet(
    val set_code: String,
    val set_name: String,
    val set_price: String,
    val set_rarity: String,
    val set_rarity_code: String
)