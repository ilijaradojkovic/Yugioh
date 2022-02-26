package com.example.yugioh.Compose

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.yugioh.room.data.ItemDB


@ExperimentalFoundationApi
@Composable
fun CardItem(
    uri_image_1: String,
    uri_image_2: String,
    id: Int,
    id1: Int,
    navigateToCardScene: (id: Int) -> Unit,




    ){


    Row(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.3f)
    ){
     Box(modifier = Modifier
         .weight(1f)
         .fillMaxHeight()
         .padding(3.dp)
         .combinedClickable(
             onClick = { navigateToCardScene(id) },

             )




        ,contentAlignment = Alignment.Center){

    Image(rememberImagePainter(data = uri_image_1),"",modifier=Modifier.size(300.dp),)
     }
       Box(modifier = Modifier
           .weight(1f)
           .fillMaxHeight()
           .padding(3.dp)
           .combinedClickable(
               onClick = { navigateToCardScene(id1) },

               )
       ){
           Image(rememberImagePainter(data = uri_image_2),"",modifier=Modifier.size(300.dp))

       }

   }
}

@ExperimentalFoundationApi
@Composable
fun CardItemForDeck(
    item:ItemDB,
    navigateToCardScene: (id: Int) -> Unit,

    changeSelected: (Boolean, ItemDB) -> Unit,

    listaSelect: SnapshotStateList<ItemDB>,
    item1: ItemDB,
    item2: ItemDB

){
    //kada skrolam dole ono izbrise selected
    var selectedCard1 by  remember{ mutableStateOf(listaSelect.contains(item1))}
    var selectedCard2 by remember{ mutableStateOf(listaSelect.contains(item2))}
LaunchedEffect(key1 = selectedCard1 ){
    selectedCard1=listaSelect.contains(item1)
}
    LaunchedEffect(key1 = selectedCard2 ){
        selectedCard2=listaSelect.contains(item2)
    }
    val stateSelectedCard1 by animateColorAsState(targetValue = if(!selectedCard1) Color.Transparent else Color.Blue)
    val    stateSelectedCard2 by animateColorAsState(targetValue =if(!selectedCard2) Color.Transparent else Color.Blue)

    Row(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.3f)
    ){
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(3.dp)
            .combinedClickable(
                onClick = { navigateToCardScene(item.id) },
                onLongClick = {
                    selectedCard1 = if (selectedCard1) {
                        changeSelected(false, item)
                        false
                    } else {
                        changeSelected(true, item)
                        true
                    }


                }
            )
            .background(stateSelectedCard1)




            ,contentAlignment = Alignment.Center){

            Image(rememberImagePainter(data = item.dataMonsterCard.card_images[0].image_url),"",modifier=Modifier.size(300.dp),)
        }
//        Box(modifier = Modifier
//            .weight(1f)
//            .fillMaxHeight()
//            .padding(3.dp)
//            .combinedClickable(
//                onClick = { navigateToCardScene(item.id) },
//                onLongClick = {
//
//                    if (selectedCard2) {
//                        changeSelected(false, item)
//                        selectedCard2 = false
//                    } else {
//                        changeSelected(true, item)
//                        selectedCard2 = true
//                    }
//
//
//                }
//            )
//            .background(stateSelectedCard2)
//        ){
//            Image(rememberImagePainter(data = item.dataMonsterCard.card_images[0].image_url),"",modifier=Modifier.size(300.dp))
//
//        }

    }
}
