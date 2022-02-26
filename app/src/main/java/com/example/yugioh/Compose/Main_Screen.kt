package com.example.yugioh.Compose


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.yugioh.Card_Type
import com.example.yugioh.Constants.maxCardsRequest
import com.example.yugioh.R
import com.example.yugioh.SearchBarState
import com.example.yugioh.data.remote.responses.monster_card.Data_Monster_Card
import com.example.yugioh.data.remote.responses.universal.Card
import com.example.yugioh.ui.theme.BorderColor

import com.example.yugioh.view_model.ViewModel
import kotlinx.coroutines.*


//glavna fora je sto si radio sa listom pa ne moze mutablestateof jer ce to samo da proimeni state i recompose ce ga jer je state kao 1 a ako radis sa listom moras mutablestateListof

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun MainScreen(navigateToCardScene: (id: Int) -> Unit, viewmodel: ViewModel) {

val card_List = viewmodel.all_Monsters
    val card_Search = viewmodel.searchData
    val isLoading =viewmodel.isLoading
    var indexSelected by remember{ mutableStateOf(0)}
    val scaffoldState = rememberScaffoldState()

    Scaffold(scaffoldState = scaffoldState,topBar = {
        TabRow(selectedTabIndex =indexSelected ,modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),backgroundColor = Color.Red) {
            Tab(selected = indexSelected==0, onClick = { indexSelected=0 },selectedContentColor = Color.White,unselectedContentColor = Color.Black) {
                Icon(painterResource(id = R.drawable.list),"List Icon")
            }
            Tab(selected = indexSelected==1, onClick = { indexSelected=1},selectedContentColor = Color.White,unselectedContentColor = Color.Black) {
                Icon(painterResource(id = R.drawable.heart),"List Icon")
            }
        }},modifier = Modifier.fillMaxSize()) {
        if(indexSelected==0) {
            if (isLoading) {
                ShowLoading()
            }
            LaunchedEffect(key1 = true, block = {
                if (card_List.isEmpty())
                    viewmodel.getAllCards(10)
            })


            PreContentScreen(navigateToCardScene, viewmodel, card_List, card_Search)
        }
        else{
            ListScreen(viewmodel = viewmodel)
        }
    }






}

@Composable
fun CreateButtonBottom(showButtonArrowUp: Boolean, animateToTop: () -> Unit) {
    if(showButtonArrowUp)
        Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.Center) {
            IconButton(onClick = {animateToTop()}) {
                Icon(Icons.Filled.ArrowUpward,"",tint= Color.White)
            }
        }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun PreContentScreen(
    navigateToCardScene: (id: Int) -> Unit,
    viewmodel: ViewModel,
    card_List: SnapshotStateList<Data_Monster_Card>,
    card_Search: SnapshotStateList<Data_Monster_Card>,

    ) {
    var cardTypeFilter by viewmodel.selectedFilterType
    var showButtonArrowUp by remember{ mutableStateOf(false)}
    var searchText by viewmodel.searchText
    val scope  = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var searchSelected by viewmodel.searchSelected
var scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = searchText){
        if(searchText.isNotEmpty()){
            viewmodel.searchCard(searchText)
        }
    }

    showButtonArrowUp = listState.firstVisibleItemIndex > 2

    //Log.i("cao",searchText)

    //sto kada odes na drugi screen i kad ase vratis nema searchtext i nema search state
Scaffold(scaffoldState = scaffoldState,bottomBar = {
    CreateButtonBottom(showButtonArrowUp) { scope.launch {listState.animateScrollToItem(0) } }
}) {


    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = listState){
       item{
           Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.Center){
               Image(painter = painterResource(id = R.drawable.logo), "Logo")
           }

        }


            stickyHeader {
                Box(modifier = Modifier
                    .fillMaxWidth()
                   ,contentAlignment = Alignment.Center) {
                    CreateSerachBar(
                        viewmodel,
                        searchText,
                        { viewmodel.changeSearch(it) },
                        searchSelected,
                        { viewmodel.changeSearch(it) },cardTypeFilter,{viewmodel.ChangeTypeCard(it)})
                }
        }

            if (searchText.isNotEmpty()
            ) {
                itemsIndexed(card_Search){index,item->
                    if(index==card_Search.lastIndex){

                        viewmodel.getAditionalCardByName(name=searchText)

                    }
                    // if((index+1)%2==0){
                    CardItem(
                        item.card_images[0].image_url,
                        item.card_images[0].image_url,
                        item.id,
                        item.id,
                        navigateToCardScene,

                    )
                    // }else cardBefore=item
                }
            } else 

                itemsIndexed(card_List){index,item->
                    if(index==card_List.lastIndex){

                        viewmodel.getAditionalCards(maxCardsRequest)

                    }

                    // if((index+1)%2==0){
                    CardItem(
                        item.card_images[0].image_url,
                        item.card_images[0].image_url,
                        item.id,
                        item.id,
                        navigateToCardScene,

                    )
                    // }else cardBefore=item

//                    if(index%2!=0){
//                        CardItem(uri_image_1 = item.card_images[0].image_url,
//                            uri_image_2 = before.card_images[0].image_url,
//                            id =item.id , id1 =before.id ,
//                            navigateToCardScene ={} )
//                    }
//                    else if (index==card_List.lastIndex)     CardItem(uri_image_1 = item.card_images[0].image_url,
//                        uri_image_2 = "",
//                        id =item.id , id1 =-1 ,
//                        navigateToCardScene ={} )
//                    else before=item

                }



            }
//
        }
}
    }






@Composable
fun ShowLoading(){
    Box(Modifier.fillMaxSize(),contentAlignment = Alignment.Center){    CircularProgressIndicator()}

}


@ExperimentalAnimationApi
@Composable
fun CreateSerachBar(
    viewmodel: ViewModel,
    text: String,
    onTextChange: (String) -> Unit,
    searchSelected: SearchBarState,
    changeSearchState: (SearchBarState) -> Unit,
    cardTypeFilter:Card_Type,
    changeType:(Card_Type)->Unit
) {

    var showDialog by remember{ mutableStateOf(false)}
var show by remember{ mutableStateOf(false)}
val sizeTextF by animateFloatAsState(targetValue = if(searchSelected==SearchBarState.SEARCH) 0.7f else 0.3f,animationSpec = tween(700))
val sizeTextField by animateFloatAsState(targetValue = if(show) 3f else 0.1f,animationSpec = tween(500))
val sizeCircleFiler by animateFloatAsState(targetValue = if(searchSelected==SearchBarState.SEARCH) 0f else 70f,animationSpec = tween(1000))
    val sizeCircleSearch by animateFloatAsState(targetValue = if(searchSelected==SearchBarState.FILTER) 0f else 70f,animationSpec = tween(1000))
    LaunchedEffect(key1 = sizeTextF ){
        show = sizeTextF==0.7f && searchSelected==SearchBarState.SEARCH
    }

    if(searchSelected==SearchBarState.FILTER && showDialog){
        Dialog(onDismissRequest = {}) {
            Column(modifier = Modifier
                .fillMaxWidth().wrapContentHeight()
                .background(Color.Gray)
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .padding(10.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceAround){
                    RadioButton(selected = cardTypeFilter==Card_Type.NORMAL_Card,onClick = {changeType(Card_Type.NORMAL_Card)} )
                    Text("Normal Card")

                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceAround){
                    RadioButton(selected = cardTypeFilter==Card_Type.EFFECT_Card,onClick = {changeType(Card_Type.EFFECT_Card)})
                    Text("Effect Card")
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceAround){
                    RadioButton(selected = cardTypeFilter==Card_Type.FUSION_Card,onClick = {changeType(Card_Type.FUSION_Card)} )
                    Text("Fusion Card")
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceAround){
                    RadioButton(selected = cardTypeFilter==Card_Type.XYZ_Card,onClick = {changeType(Card_Type.XYZ_Card)} )
                    Text("XYZ Card")
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceAround){
                    RadioButton(selected = cardTypeFilter==Card_Type.FLIP_Card,onClick = {changeType(Card_Type.FLIP_Card)} )
                    Text("Flip Card")
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceAround){
                    RadioButton(selected = cardTypeFilter==Card_Type.TUNER_Card,onClick = {changeType(Card_Type.TUNER_Card)} )
                    Text("Tuner Card")
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceAround){
                    RadioButton(selected = cardTypeFilter==Card_Type.SYNCHRO_Card,onClick = {changeType(Card_Type.SYNCHRO_Card)} )
                    Text("Synchro Card")
                }

                Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceAround){
                    Button(onClick = {  showDialog=false}) {
                        Text("Cancle")
                    }
                    Button(onClick = { viewmodel.getAllCardsFilter(10);showDialog=false }) {
                        Text("Filter")
                    }
                }




            }
        }
    }
    LaunchedEffect(key1 = show, block ={if(!show) onTextChange("")} )
    Row(
        modifier = Modifier
            .fillMaxSize(),

        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {


        Box( modifier = Modifier

            .fillMaxWidth(sizeTextF)
            .height(IntrinsicSize.Max)
            .border(1.dp, MaterialTheme.colors.BorderColor(), RoundedCornerShape(10.dp))
            .background(Color.Black, shape = RoundedCornerShape(10.dp))) {
            Row(
                Modifier
                    .fillMaxSize()
                    .padding(3.dp)
            ) {


                IconButton(onClick = {changeSearchState(SearchBarState.FILTER); showDialog=true }, modifier = Modifier
                    .weight(1f)
                    .drawBehind {
                        if (searchSelected == SearchBarState.FILTER)
                            drawCircle(Color.Cyan, radius = sizeCircleFiler)
                    }) {
                    Icon(painterResource(id = R.drawable.filetr), "Filter", tint = Color.Red)
                }
                IconButton(onClick = {changeSearchState(SearchBarState.SEARCH) ; showDialog=false}, modifier = Modifier
                    .weight(1f)
                    .drawBehind {
                        if (searchSelected == SearchBarState.SEARCH)
                            drawCircle(Color.Cyan, radius = sizeCircleSearch)
                    }) {
                    Icon(painterResource(id = R.drawable.search), "Saerch", tint = Color.Red)
                }

                   Box(modifier = Modifier
                       .weight(sizeTextField)

                       ,contentAlignment = Alignment.Center){
                       BasicTextField(modifier = Modifier
                           .fillMaxWidth()
                      ,enabled = show,value = text, onValueChange = {onTextChange(it)} ,cursorBrush = SolidColor(Color.DarkGray),textStyle = TextStyle(color = Color.White),maxLines = 1
                       ){
                               innerTextField ->
                           Row(modifier = Modifier,verticalAlignment = Alignment.CenterVertically){
                               innerTextField()
                               IconButton(onClick = {onTextChange("")}) {
                                   Icon(Icons.Filled.Close,"",tint = Color.White)

                               }
                           }


                       }

               }
            }
        }
    }
}


