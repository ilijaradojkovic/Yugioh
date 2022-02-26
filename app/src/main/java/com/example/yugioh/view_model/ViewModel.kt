package com.example.yugioh.view_model

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yugioh.Action
import com.example.yugioh.Card_Type
import com.example.yugioh.Constants.maxCardsRequest
import com.example.yugioh.Response
import com.example.yugioh.SearchBarState
import com.example.yugioh.data.remote.responses.monster_card.Data_Monster_Card
import com.example.yugioh.repository.YugiohRepository
import com.example.yugioh.room.data.ItemDB
import com.example.yugioh.room.data.RoomResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class ViewModel @Inject constructor(private val yugiohRepository: YugiohRepository):ViewModel() {


    var selectedFilterType= mutableStateOf(Card_Type.NORMAL_Card)
    fun ChangeTypeCard(type:Card_Type){
        selectedFilterType.value=type
    }

   var offset =0


    //STATE BAR
    //-----------------------------------------------------------------
    var searchSelected = mutableStateOf(SearchBarState.NONE)

    fun changeSearch(searchBarState: SearchBarState){
        searchSelected.value=searchBarState
    }
    //-----------------------------------------------------------
    //SEARCH TEXT
    //----------------------------------------------------------
    var searchText = mutableStateOf("")
    fun changeSearch(text: String){
        searchText.value=text
    }
    //----------------------------------------------------------
    private  var _all_Monsters = mutableStateListOf<Data_Monster_Card>()
    val all_Monsters =_all_Monsters

    private  var _searchData = mutableStateListOf<Data_Monster_Card>()
    val searchData =_searchData


    private  var _spesific_Card = mutableStateOf<Data_Monster_Card?>(null)
    val spesific_Card =_spesific_Card

        var isLoading by mutableStateOf(true)

    fun getAllCards(count:Int){
        isLoading=true
            viewModelScope.launch (Dispatchers.IO){

                val result=yugiohRepository.getMonsters(count,offset ,selectedFilterType.value.type)
                when(result){

                    is Response.Success->{

                        _all_Monsters.addAll(result.dataSucceded.data)

                        isLoading=false
                    }
                    is Response.Error->{}
                    else->{}

                }


            }
    }
    fun getAllCardsFilter(count:Int){
        isLoading=true
        viewModelScope.launch (Dispatchers.IO){

            val result=yugiohRepository.getMonsters(count,offset ,selectedFilterType.value.type)
            when(result){

                is Response.Success->{

                    _all_Monsters.clear()
                    _all_Monsters.addAll(result.dataSucceded.data)

                    isLoading=false
                }
                is Response.Error->{}
                else->{}

            }


        }
    }
    fun getAditionalCards(count: Int){
            viewModelScope.launch (Dispatchers.IO){
                offset=_all_Monsters.size
                val result=yugiohRepository.getMonsters(count,offset ,selectedFilterType.value.type )
                when(result){

                    is Response.Success->{

                       _all_Monsters.addAll(result.dataSucceded.data)
                        isLoading=false

                    }
                    is Response.Error->{}
                    else->{}

                }


            }
    }
    fun getAditionalCardByName(name:String){
        isLoading=true
        viewModelScope.launch (Dispatchers.IO){
            offset=_searchData.size
            val result=yugiohRepository.searchMonsters(maxCardsRequest,offset ,"%$name%" )
            when(result){

                is Response.Success->{

                    _searchData.addAll(result.dataSucceded.data)
                    isLoading=false

                }
                is Response.Error->{}
                else->{}

            }


        }
    }

    fun searchCard(text:String){

        _searchData.clear()
        isLoading=true
        viewModelScope.launch (Dispatchers.IO){
            val result=yugiohRepository.searchMonsters(maxCardsRequest,offset,"%$text%")
            when(result){

                is Response.Success->{
                    _searchData.addAll(result.dataSucceded.data)
                    isLoading=false
                }
                is Response.Error->{}
                else->{}

            }


        }
    }

    fun getSpesificMosnterCard(id: Int) {
        isLoading=true
        viewModelScope.launch (Dispatchers.IO){
            val result=yugiohRepository.getSpesificMonster(id)
            when(result){

                is Response.Success->{
                    _spesific_Card.value=result.dataSucceded.data.first()
                    isLoading=false

                }
                is Response.Error->{}
                else->{}

            }


        }
    }

    fun resetSpesificCard(){
        _spesific_Card.value=null
    }
    fun resetAllCards(){
        _allSavedCards.value=RoomResponse.Loading()
    }

    //=====================================================================

   private var dataMonsterCard by  _spesific_Card

    var deckName by mutableStateOf("")
    private var _allSavedCards =  MutableStateFlow<RoomResponse<List<ItemDB>>>(RoomResponse.Loading())
    val allSavedCards = _allSavedCards
    //val minPriceForDeck= _allSavedCards.value.data?.minOf { it.dataMonsterCard.card_prices.minOf { item-> min(item.tcgplayer_price.toFloat(),item.cardmarket_price.toFloat())


        //==================================
    private var _allDecks=  MutableStateFlow<List<String>>(emptyList())
    val allDecks = _allDecks


//stavi lisu da bude ovde ona je logika lista za delete

    var listForDelete = mutableStateListOf<ItemDB>()

    fun AddItem(itemDB: ItemDB){
        listForDelete.add(itemDB)
    }
    fun RemoveITem(itemDB: ItemDB){
        listForDelete.remove(itemDB)
    }



    fun HandleAction(action:Action){
        when(action){
            Action.INSERT->{
                if(dataMonsterCard!=null)
                    //if(deckName.isEmpty) ShowToastMessage
                    insertData(ItemDB(deckName = deckName,dataMonsterCard= dataMonsterCard!!))
            }
            Action.DELETE->{}
            Action.UPDATE->{}
            else->{}
        }
    }
    fun getAllCardsSavedForDeck(deckName:String){
        _allSavedCards.value=RoomResponse.Loading()
        viewModelScope.launch (Dispatchers.IO){
            try{
                yugiohRepository.GetDeck(deckName).collect {
                    _allSavedCards.value=RoomResponse.Data(it)
                }
            }catch (e:Exception){
                _allSavedCards.value=RoomResponse.Error("error")
            }


        }

    }

fun DeleteCardRange(lista:List<ItemDB>){
    try{
        viewModelScope.launch(Dispatchers.IO) {
            lista.forEach {
                yugiohRepository.DeleteItem(it)
            }
            listForDelete.clear()
        }
    }catch (e:Exception){
        Log.i("cao","error while deleting")
    }
    finally {

    }

}
    fun insertData(itemDB: ItemDB){
        viewModelScope.launch (Dispatchers.IO){

            yugiohRepository.InsertItem(itemDB)

        }
    }
    fun getDecks(){

        viewModelScope.launch {

            try{
            yugiohRepository.GetDecks().collect {
                _allDecks.value=it
            }}
            catch (e:Exception){
                _allDecks.value= emptyList()
                Log.i("cao","Error")
            }

        }
    }

}