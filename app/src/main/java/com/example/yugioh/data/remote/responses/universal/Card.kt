package com.example.yugioh.data.remote.responses.universal

import com.example.yugioh.data.remote.responses.magic_card.Magic_Card
import com.example.yugioh.data.remote.responses.monster_card.Monster_Card

data class Card(val listaMonster:List<Monster_Card>,val listMagic:List<Magic_Card>)
