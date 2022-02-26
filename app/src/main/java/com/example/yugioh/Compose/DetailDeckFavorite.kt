package com.example.yugioh.Compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.yugioh.R
import com.example.yugioh.data.remote.responses.monster_card.Data_Monster_Card
import com.example.yugioh.room.data.ItemDB
import com.example.yugioh.view_model.ViewModel
import kotlin.math.min

@ExperimentalFoundationApi
@Composable
fun DetailDeck(viewModel: ViewModel, monsterCard: List<ItemDB>?, ChangeDeck: (String) -> Unit,deckToGo:String){
    var before =ItemDB(dataMonsterCard = Data_Monster_Card())
    var indexBefore=-1
    val scaffoldState = rememberScaffoldState()
    var selected by remember{ mutableStateOf(false)}
    var listForDelete =viewModel.listForDelete

var sum by remember{
    mutableStateOf(0f)}
    LaunchedEffect(key1 = true, block ={
        monsterCard?.forEach { sum+=it.dataMonsterCard.card_prices.minOf { item-> min(item.tcgplayer_price.toFloat(),item.cardmarket_price.toFloat()) } }
    })

    Scaffold(modifier = Modifier.fillMaxSize(),scaffoldState = scaffoldState,topBar = { TopBar(listForDelete.isNotEmpty(),listForDelete.size,listForDelete,viewModel) }) {


        LazyColumn(modifier = Modifier.fillMaxSize()) {
            monsterCard?.let {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.White)
                            .padding(10.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { ChangeDeck("") }, modifier = Modifier.weight(1f)) {
                            Icon(painterResource(id = R.drawable.back), "")
                        }
                        Text("Min Cost $sum", modifier = Modifier.weight(1f))

                        Text(
                            "${monsterCard.size}",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                itemsIndexed(monsterCard,{ index, _ ->index}) { index, item ->


//                    if (index % 2 != 0) {
                        CardItemForDeck(item,
                            navigateToCardScene = {},

                            changeSelected = { isSelected, item-> selected = isSelected;if(isSelected)listForDelete.add(item) else listForDelete.remove(item) },
                            listForDelete,
                            item,
                            item)
//                        )
//                    } else if (index == monsterCard.lastIndex) CardItemForDeck(uri_image_1 = item.dataMonsterCard.card_images[0].image_url,
//                        uri_image_2 = "",
//                        id = item.dataMonsterCard.id, id1 = -1,
//                        navigateToCardScene = {},
//                    { isSelected,id-> selected = isSelected;if(isSelected)listForDelete.add(id) else listForDelete.remove(id)},listForDelete,index ,-1)
//                    else {
//                        before = item
//                        indexBefore=index
//                    }

                }
            }

        }
    }


}



