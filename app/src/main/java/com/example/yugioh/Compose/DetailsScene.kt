package com.example.yugioh.Compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.example.yugioh.Action
import com.example.yugioh.Card_Type
import com.example.yugioh.R
import com.example.yugioh.data.remote.responses.universal.CardPrice
import com.example.yugioh.data.remote.responses.universal.CardSet
import com.example.yugioh.ui.theme.ProgressBarColor
import com.example.yugioh.ui.theme.TextColor
import com.example.yugioh.view_model.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun DetailsScene(id:Int,viewmodel:ViewModel,NavigateToMainScene:()->Unit){
    val card = viewmodel.spesific_Card.component1()
    var decks = viewmodel.allDecks.collectAsState()
    LaunchedEffect(key1 = true, block ={viewmodel.getDecks()} )
    LaunchedEffect(key1 = true){
        viewmodel.getSpesificMosnterCard(id)
    }

    var bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    val corutineScope = rememberCoroutineScope()

 

    if(card!=null)
        when(card.type){
            Card_Type.PENDALUM_EFFECT_FUSION_Card.type,
            Card_Type.SPIRIT_Card.type,
            Card_Type.PENDALUM_TUNER_EFFECT_Card.type,
            Card_Type.EFFECT_Card.type,
            Card_Type.TUNER_Card.type,
            Card_Type.FLIP_Card.type,
            Card_Type.GEMINI_Card.type,
            Card_Type.PENDALUM_Card.type,
            Card_Type.RITUAL_Card.type,
            Card_Type.TOON_Card.type,
            Card_Type.SYNCHRO_Card.type,
            Card_Type.SYNCHRO_TUNER_Card.type,
            Card_Type.PENDALUM_FLIP_EFFECT_Card.type,
            Card_Type.RITUAL_EFFECT_Card.type,
            Card_Type.SYNCHRO_PENDULUM_EFFECT_Card.type,
            Card_Type.UNION_EFFECT_Card.type,
            Card_Type.FLIP_EFFECT_Card.type,
            Card_Type.FUSION_Card.type,
            Card_Type.TOKEN.type,
            Card_Type.FLIP_TUNER_EFFECT_Card.type,  Card_Type.NORMAL_Card.type,Card_Type.NORMAL_TUNER_Card.type,Card_Type.PENDALUM_NORMAL_Card.type->{CreateMonsterDetailScreen(decks.value,
                card.atk,
                card.def,
                card.desc,
                card.attribute,
                card.level,
                card.type,
                card.card_images.first().image_url,
                painterResource(id = R.drawable.level),
                card.card_prices,
                card.card_sets,
                viewmodel,
                bottomSheetScaffoldState,
                corutineScope
            )}






            Card_Type.LINK_Card.type, Card_Type.XYZ_Card.type,Card_Type.XYZ_PENDALUM_EFFECT_Card.type->{CreateMonsterDetailScreen(decks.value,card.atk,card.def,card.desc,card.attribute,card.level,card.type,card.card_images.first().image_url,
                painterResource(id = R.drawable.rank),card.card_prices,card.card_sets,viewmodel, bottomSheetScaffoldState,corutineScope)}

        else->{CreateMagicCardScreen(viewmodel,card.card_images.first().image_url,card.type,card.desc,card.card_prices,card.card_sets,bottomSheetScaffoldState,corutineScope,decks.value)}

        }

    }





@ExperimentalMaterialApi
@Composable
fun CreateMonsterDetailScreen(
    decks: List<String>,
    atk: Int,
    def: Int,
    desc: String,
    attribute: String,
    level: Int,
    type: String,
    urlImg: String,
    painter: Painter,
    cardPrices: List<CardPrice>,
    cardSets: List<CardSet>,
    viewmodel: ViewModel,
    bottomSheetScaffoldState:BottomSheetScaffoldState,
    coroutineScope: CoroutineScope
) {







    //viewmodel.HandleAction(Action.INSERT)


    CreateContentDeatilScreen(viewmodel,atk,def,desc,attribute,level,type,urlImg,painter,cardPrices,cardSets,bottomSheetScaffoldState,coroutineScope,decks)
}




@ExperimentalMaterialApi
@Composable
fun CreateContentDeatilScreen(
    viewmodel: ViewModel,
    atk: Int,
    def: Int,
    desc: String,
    attribute: String,
    level: Int,
    type: String,
    urlImg: String,
    painter: Painter,
    cardPrices: List<CardPrice>,
    cardSets: List<CardSet>,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    corutineScope: CoroutineScope,
    decks: List<String>
) {
    var showDialog by remember{ mutableStateOf(false)}
    var text by remember{ mutableStateOf("")}
    var isSheetOpened by remember{ mutableStateOf(false)}
    BottomSheetScaffold(scaffoldState = bottomSheetScaffoldState,sheetContent = {SheetContent(decks,viewmodel,text,{text=it},showDialog, {
        showDialog = it
        isSheetOpened=showDialog
        corutineScope.launch { if (showDialog) bottomSheetScaffoldState.bottomSheetState.expand() else bottomSheetScaffoldState.bottomSheetState.collapse() }
    })
    },sheetPeekHeight = 0.dp,modifier = Modifier.fillMaxSize() ,sheetGesturesEnabled = false) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            item() {
                Box(
                    Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.Center
                )
                {
                    IconButton(onClick = {isSheetOpened=!isSheetOpened; corutineScope.launch { if(isSheetOpened) bottomSheetScaffoldState.bottomSheetState.expand() else bottomSheetScaffoldState.bottomSheetState.collapse() }}) {
                        Icon(painterResource(id = R.drawable.heart), "Hearth", tint = Color.Red)
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight(0.5f), contentAlignment = Alignment.Center
                )
                {
                    Image(
                        painter = rememberImagePainter(urlImg),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "CardImg"
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Box(
                    Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.Center
                )
                {
                    CreateBar(Color.Red, atk, "ATK")
                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Box(
                    Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.Center
                )
                {
                    CreateBar(Color.Blue, def, "DEF")
                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Box(
                    Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.CenterStart
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Level", color = MaterialTheme.colors.TextColor())
                        Spacer(modifier = Modifier.width(10.dp))
                        for (i in 0 until level) {
                            Image(
                                painter = painter,
                                contentDescription = "levelIMG",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }

                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Box(
                    Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.CenterStart
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Attribute", color = MaterialTheme.colors.TextColor())
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = attribute, color = MaterialTheme.colors.TextColor())
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Box(
                    Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Type", color = MaterialTheme.colors.TextColor())
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = type, color = MaterialTheme.colors.TextColor())
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Box(
                    Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.CenterStart
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Effect", color = MaterialTheme.colors.TextColor())
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = desc, color = MaterialTheme.colors.TextColor())
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Box(modifier = Modifier.fillMaxWidth()) {

                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            "Prices: ",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (i in cardPrices) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Card Market Price:", color = Color.White)
                                Text(i.cardmarket_price, color = Color.White)
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Amazon Price:", color = Color.White)
                                Text(i.amazon_price, color = Color.White)
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("TCG Price:", color = Color.White)
                                Text(i.tcgplayer_price, color = Color.White)
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("coolstuffinc Price:", color = Color.White)
                                Text(i.coolstuffinc_price, color = Color.White)
                            }


                        }
                    }
                }


            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            "Where To Get: ",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (i in cardSets) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${i.set_name}:", color = Color.White)
                                Text(i.set_price, color = Color.White)
                            }

                        }
                    }
                }

            }

        }
    }

}


@Composable
fun SheetContent(
    decks: List<String>,
    viewmodel: ViewModel,
    text: String,
    changeText: (String) -> Unit,
    showDialog: Boolean,
    changeBool: (Boolean) -> Unit
) {

    CreateCustomDialog(showDialog,changeBool,text,changeText,viewmodel)
    Column(
        Modifier
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth()
            .fillMaxHeight(0.45f)
            .background(Color.Red),horizontalAlignment = Alignment.CenterHorizontally){

            Box(modifier= Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),contentAlignment = Alignment.Center){
                IconButton(onClick = { changeBool(!showDialog) }) {
                    Icon(painterResource(id = R.drawable.add),"Add deck",tint=Color.Black)
                }
            }

        for(i in decks)
            Box(modifier= Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),contentAlignment = Alignment.Center){
                Text(text = i,modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        changeBool(false);viewmodel.deckName = i;viewmodel.HandleAction(
                        Action.INSERT
                    )
                    }
                   ,textAlign = TextAlign.Center,fontSize = 20.sp)
                Spacer(modifier = Modifier.height(50.dp))
            }
       

       }
    }


@Composable
fun CreateCustomDialog(
    showDialog: Boolean,
    change: (Boolean) -> Unit,
    text: String,
    onChangeText: (String) -> Unit,
    viewmodel: ViewModel
) {
    if(showDialog)
    Dialog(onDismissRequest = {change(false)}) {
        Column(modifier= Modifier
            .clip(RoundedCornerShape(15.dp))
            .background(Color.DarkGray)
            .padding(10.dp),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.SpaceEvenly) {
            Text("Napravi Deck",color = Color.White,fontWeight = FontWeight.Bold,fontSize = 20.sp)
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = text, onValueChange ={onChangeText(it)},
                Modifier
                    .border(1.dp, Color.Red)
                    .clip(RoundedCornerShape(10.dp)),colors = TextFieldDefaults.textFieldColors(disabledIndicatorColor = Color.Transparent,focusedIndicatorColor = Color.Transparent,unfocusedIndicatorColor = Color.Transparent,errorIndicatorColor = Color.Transparent,backgroundColor = Color.Transparent,textColor = Color.Red),label = {Text("Deck Name")} )
            Spacer(modifier = Modifier.height(10.dp))
           Row(modifier= Modifier
               .fillMaxWidth()
               .height(IntrinsicSize.Max)){
               Button(onClick = { change(false) },modifier = Modifier.weight(1f)) {
                    Text("Cancle")
               }
               Spacer(modifier = Modifier.weight(1f))
               Button(onClick = { change(false);viewmodel.deckName=text;viewmodel.HandleAction(Action.INSERT)},modifier = Modifier.weight(1f)) {
                   Text("Create")
               }
           }

        }
    }
}

@Composable
fun CreateBar(colorToFill:Color,point:Int,typeBarFor:String){
    var animate by remember{ mutableStateOf(false)}
val progress by animateFloatAsState(targetValue = if(animate) point/5000f else 0f,tween(2000))
    LaunchedEffect(key1 = true){
        animate=true
    }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {
        Text(typeBarFor,color = MaterialTheme.colors.TextColor())
        Spacer(modifier = Modifier.height(5.dp))
        Box(modifier= Modifier
            .fillMaxWidth()
            .height(30.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.ProgressBarColor())){
            Row(modifier= Modifier
                .height(30.dp)
                .fillMaxWidth(progress)
                .background(colorToFill)
                .padding(4.dp),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween){
                Text("$point",color = MaterialTheme.colors.TextColor())
                Text(((100*point/5000).toString()+"%"),color = MaterialTheme.colors.TextColor())
            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun CreateMagicCardScreen(
    viewmodel: ViewModel,
    urlImg: String,
    type: String,
    desc: String,
    cardPrices: List<CardPrice>,
    cardSets: List<CardSet>,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    corutineScope: CoroutineScope,
    decks: List<String>
) {
    val scrollstate = rememberScrollState()
    var showDialog by remember{ mutableStateOf(false)}
    var text by remember{ mutableStateOf("")}
    var isSheetOpened by remember{ mutableStateOf(false)}
    LaunchedEffect(key1 = isSheetOpened){
        if(isSheetOpened) bottomSheetScaffoldState.bottomSheetState.expand() else bottomSheetScaffoldState.bottomSheetState.collapse()
    }
    BottomSheetScaffold(scaffoldState = bottomSheetScaffoldState,sheetContent = {SheetContent(decks,viewmodel,text,{text=it},showDialog, {
        showDialog = it
        isSheetOpened=showDialog
        corutineScope.launch { if (showDialog) bottomSheetScaffoldState.bottomSheetState.expand() else bottomSheetScaffoldState.bottomSheetState.collapse() }
    })
    },sheetPeekHeight = 0.dp,modifier = Modifier.fillMaxSize() ,sheetGesturesEnabled = false) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            item {
                Box(
                    Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.Center
                )
                {
                    AddButton { isSheetOpened = !isSheetOpened }


                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillParentMaxHeight(0.5f), contentAlignment = Alignment.Center
                )
                {
                    Image(
                        painter = rememberImagePainter(urlImg),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "CardImg"
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Box(
                    Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Type", color = MaterialTheme.colors.TextColor())
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = type, color = MaterialTheme.colors.TextColor())
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Box(
                    Modifier
                        .fillMaxWidth(), contentAlignment = Alignment.CenterStart
                )
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Effect", color = MaterialTheme.colors.TextColor())
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = desc, color = MaterialTheme.colors.TextColor())
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            "Prices: ",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (i in cardPrices) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Card Market Price:", color = Color.White)
                                Text(i.cardmarket_price, color = Color.White)
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Amazon Price:", color = Color.White)
                                Text(i.amazon_price, color = Color.White)
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("TCG Price:", color = Color.White)
                                Text(i.tcgplayer_price, color = Color.White)
                            }
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("coolstuffinc Price:", color = Color.White)
                                Text(i.coolstuffinc_price, color = Color.White)
                            }


                        }
                    }
                }


            }
            item { Spacer(modifier = Modifier.height(25.dp)) }
            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            "Where To Get: ",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        for (i in cardSets) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${i.set_name}:", color = Color.White)
                                Text(i.set_price, color = Color.White)
                            }

                        }
                    }
                }

            }
        }
    }
    }

