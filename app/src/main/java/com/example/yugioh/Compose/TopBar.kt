package com.example.yugioh.Compose

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yugioh.room.data.ItemDB
import com.example.yugioh.view_model.ViewModel


@Composable
fun TopBar(selected:Boolean, itemsSelecetd:Int, listForDelete: List<ItemDB>, viewModel: ViewModel){
    val animateHeight by animateDpAsState(targetValue = if(selected) 30.dp else 0.dp)
    TopAppBar(modifier = Modifier.fillMaxWidth().height(animateHeight),backgroundColor = Color.DarkGray) {
        Row(modifier=Modifier.fillMaxSize(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.End){
            Text("$itemsSelecetd",color = Color.Red)
            IconButton( onClick={ viewModel.DeleteCardRange(lista =listForDelete)}){
                Icon( Icons.Filled.Delete, contentDescription ="" ,tint=Color.Red)
            }

        }

    }
}