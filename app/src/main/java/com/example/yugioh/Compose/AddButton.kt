package com.example.yugioh.Compose

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddButton(ChangeIsSheetOpened: () -> Unit) {

    var liked by remember{ mutableStateOf(false) }
    //  val colorState by animateColorAsState(targetValue = if(liked) Color.Red else Color.Black,animationSpec = tween(700))
    var stateHeart by remember {
        mutableStateOf(StateHeart.IDLE)
    }
    val sizeState by animateDpAsState(targetValue = if(stateHeart==StateHeart.IDLE) 30.dp else 70.dp,
    )
    LaunchedEffect(key1 = sizeState){
        Log.i("cao",sizeState.toString())
        if(sizeState==70.dp)
            stateHeart=StateHeart.IDLE

    }

    Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center){
        IconButton(onClick = {ChangeIsSheetOpened();liked=!liked;stateHeart=StateHeart.EXPANDED},modifier= Modifier.fillMaxSize()){
            Icon(Icons.Filled.Add,"",tint = Color.Red,modifier = Modifier.size(sizeState))
        }
    }

}
enum class StateHeart{
    IDLE,EXPANDED
}
