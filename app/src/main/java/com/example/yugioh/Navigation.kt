package com.example.yugioh

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.yugioh.Compose.DetailsScene
import com.example.yugioh.Compose.MainScreen
import com.example.yugioh.Constants.cardScenePath
import com.example.yugioh.Constants.idArg_CardScene
import com.example.yugioh.Constants.mainSceneName
import com.example.yugioh.Constants.mainScenePath
import com.example.yugioh.view_model.ViewModel



@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun SetUpNavigation(navHostController: NavHostController, viewmodel: ViewModel)
{


    val screenNavigation= remember(navHostController){
        SceensNavigation(navHostController)

    }

    NavHost(navController = navHostController,startDestination = mainSceneName ){
        mainScene(screenNavigation.navigateToCardScene,viewmodel)
        cardSceene( screenNavigation.navigateToMainScene,viewmodel)
    }


}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
fun NavGraphBuilder.mainScene(
    navigateToCardScene: (Int) -> Unit,
    viewmodel: ViewModel
) {
    composable(mainScenePath){
        viewmodel.resetSpesificCard()
        MainScreen(navigateToCardScene,viewmodel)
    }

}
@ExperimentalMaterialApi
fun NavGraphBuilder.cardSceene(navigateToMainScene: () -> Unit, viewmodel: ViewModel) {
composable(cardScenePath,listOf( navArgument(idArg_CardScene){
    nullable=false
    type= NavType.IntType
})){


    val id = remember {
        it.arguments?.getInt(idArg_CardScene)
    }



    DetailsScene(id?:-1,viewmodel,navigateToMainScene);


}
}