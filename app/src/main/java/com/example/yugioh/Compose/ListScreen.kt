package com.example.yugioh.Compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yugioh.room.data.ItemDB
import com.example.yugioh.room.data.RoomResponse
import com.example.yugioh.view_model.ViewModel


@ExperimentalFoundationApi
@Composable
fun ListScreen(viewmodel: ViewModel){
    var decks = viewmodel.allDecks.collectAsState()

    LaunchedEffect(key1 = true, block ={viewmodel.getDecks()} )
    var deckToGo by remember{ mutableStateOf("")}

    val currDeckCliked = viewmodel.allSavedCards.collectAsState()

    LaunchedEffect(key1 = deckToGo, block ={
        if(deckToGo.isNotEmpty() && currDeckCliked.value is RoomResponse.Loading )
            viewmodel.getAllCardsSavedForDeck(deckToGo)
        else viewmodel.resetAllCards()
    } )
    ContentDeck(viewmodel,currDeckCliked,decks,deckToGo) { deckToGo = it }


}
@ExperimentalFoundationApi
@Composable
fun ContentDeck(
    viewmodel: ViewModel,
    currDeckCliked: State<RoomResponse<List<ItemDB>>>,
    decks: State<List<String>>,
    deckToGo:String,
    ChangeDeck: (String) -> Unit
) {
    if(currDeckCliked.value is RoomResponse.Data){
        DetailDeck(viewmodel,currDeckCliked.value.data,ChangeDeck,deckToGo)
    }
    else
        CreateDataList(decks.value, ChangeDeck,deckToGo)
}

@Composable
fun CreateDataList(data: List<String>, changeDeckToGo: (String) -> Unit, deckToGo: String) {
    var before =""
  LazyColumn(modifier = Modifier.fillMaxSize()){
    itemsIndexed(data){index, item ->

        if(index%2!=0)
            DeckProfile(item,before,changeDeckToGo,deckToGo)
        else if (index==data.lastIndex) DeckProfile(name1 = item, name2 ="" , onClick =changeDeckToGo,deckToGo )
        else before=item


    }
  }
}
@Composable
fun DeckProfile(name1: String, name2: String, onClick: (String) -> Unit, deckToGo: String)
{
    Column(
        Modifier
            .fillMaxWidth().height(150.dp)
            .padding(10.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()

            .clip(RoundedCornerShape(20.dp))
            ,horizontalArrangement = Arrangement.SpaceEvenly){
            if(name1.isNotEmpty())
                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .border(
                        BorderStroke(1.dp, Color.White), shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { onClick(name1) }
                    ,verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(name1,color = Color.White)
                }
            else Column(modifier = Modifier
                .fillMaxSize()
                .weight(1f)


                ,verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {

            }
            Spacer(modifier = Modifier.weight(0.4f))
            if(name2.isNotEmpty())
                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .border(
                        BorderStroke(1.dp, Color.White), shape = RoundedCornerShape(20.dp)
                    )
                    .clickable { onClick(name2) }
                    ,verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(name2,color = Color.White)
                }
            else Column(modifier = Modifier
                .fillMaxSize()
                .weight(1f)


                ,verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {

            }
        }


    }

}