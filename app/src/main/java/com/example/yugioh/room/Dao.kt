package com.example.yugioh.room

import androidx.room.*
import androidx.room.Dao
import com.example.yugioh.room.data.ItemDB
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

@Query("SELECT * FROM TABLE_NAME")
 fun  getAllCards(): Flow<List<ItemDB>>

@Insert()
suspend fun  InsertItem(itemDB: ItemDB)

//ne sme suspend
@Query("SELECT  * FROM TABLE_NAME WHERE deckName = :name")
 fun  GetDeck(name:String):Flow<List<ItemDB>>

@Query("SELECT  DISTINCT deckName FROM TABLE_NAME")
 fun  GetDecks():Flow<List<String>>

@Delete
 suspend fun DeleteCard(itemDB: ItemDB)
}