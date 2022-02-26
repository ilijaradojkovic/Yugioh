package com.example.yugioh.data.remote.responses

import com.example.yugioh.data.remote.responses.magic_card.Data_Magic_Card
import com.example.yugioh.data.remote.responses.magic_card.Magic_Card
import com.example.yugioh.data.remote.responses.monster_card.Data_Monster_Card
import com.example.yugioh.data.remote.responses.monster_card.Monster_Card
import com.example.yugioh.data.remote.responses.universal.Card
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YiguihAPI {


    @GET("cardinfo.php")
     fun getYugiohListMagicCard(
        @Query("num") limit:Int,
        @Query("offset") offset:Int
    ): Magic_Card

     //MONSTER

     //moraju suspend da budu nece da radi ako ne bvudu suspend jer je net request , vidi sat ti vraca api ako vraca [ pa tui podatke]  onda
     // je lista tj niz a mi ovde monster_card prima listu tih karata nebitno sto je samo 1 tako api vraca vraca listu
      @GET("cardinfo.php")
  suspend   fun getYugiohListMonsterCard(
        @Query("num") limit:Int,
        @Query("offset") offset:Int,
        @Query("type") type:String
    ): Monster_Card

    @GET("cardinfo.php")
  suspend fun  searchYugiohMonster(
      @Query("num") limit:Int,
      @Query("offset") offset: Int,
      @Query("name") name:String
  ):Monster_Card


  @GET("cardinfo.php")
    suspend fun getSpesificMonsterCard(
      @Query("id") id:Int
  ):Monster_Card


    @GET("cardinfo.php/{name}")
     fun  getCardInfo_Magic(
    @Path("name") name:String
    ):Data_Magic_Card

    @GET("cardinfo.php/{name}")
     fun  getCardInfo_Monster(
        @Path("name") name:String
    ):Data_Monster_Card
}