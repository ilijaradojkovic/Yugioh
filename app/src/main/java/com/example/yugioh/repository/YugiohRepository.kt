package com.example.yugioh.repository

import android.util.Log
import com.example.yugioh.Response
import com.example.yugioh.data.remote.responses.YiguihAPI
import com.example.yugioh.data.remote.responses.magic_card.Magic_Card
import com.example.yugioh.data.remote.responses.monster_card.Monster_Card
import com.example.yugioh.room.Dao
import com.example.yugioh.room.data.ItemDB
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject


@ActivityScoped
class YugiohRepository @Inject constructor(
    private val api:YiguihAPI,
    private val dao:Dao
){
    suspend fun  getMonsters(limit:Int,offset:Int,type:String):Response<Monster_Card>
    {
        val response = try {
            api.getYugiohListMonsterCard(limit, offset,type)
        } catch(e: Exception) {
            return Response.Error("An unknown error occured.")
        }
        return Response.Success(response)

    }
    suspend fun  searchMonsters(limit:Int,offset:Int,name:String):Response<Monster_Card> {

        val response = try {
            api.searchYugiohMonster(limit, offset,name)
        } catch(e: Exception) {
            return Response.Error("An unknown error occured.")
        }
        return Response.Success(response)
    }

    suspend fun  getSpesificMonster(id:Int): Response<Monster_Card> {
        val response = try {
            api.getSpesificMonsterCard(id)
        } catch(e: Exception) {
            return Response.Error("An unknown error occured.")
        }
        return Response.Success(response)

    }

    suspend fun  getMagic(limit:Int,offset:Int): Response<Magic_Card> {
        val response = try {
            api.getYugiohListMagicCard(limit, offset)
        } catch (e: Exception) {
            return Response.Error("Error")
        }


        return Response.Success(response)
    }

    suspend fun InsertItem(itemDB: ItemDB){
        dao.InsertItem(itemDB)
    }

    suspend fun getAllItems(): Flow<List<ItemDB>> {
       return dao.getAllCards()
    }
    suspend fun  GetDeck(name: String):Flow<List<ItemDB>>{
        return  dao.GetDeck(name)
    }
    suspend fun  GetDecks():Flow<List<String>>{
        return  dao.GetDecks()
    }

    suspend fun  DeleteItem(itemDB: ItemDB){
        Log.i("cao",itemDB.toString())
        dao.DeleteCard(itemDB)
    }


}