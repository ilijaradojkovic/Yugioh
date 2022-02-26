package com.example.yugioh.room.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yugioh.data.remote.responses.monster_card.Data_Monster_Card

@Entity(tableName = "TABLE_NAME")
data class ItemDB(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val deckName :String ="Basic",
    val dataMonsterCard:Data_Monster_Card)
