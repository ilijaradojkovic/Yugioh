package com.example.yugioh

import androidx.navigation.NavHostController
import com.example.yugioh.Constants.cardSceneName
import com.example.yugioh.Constants.cardScenePath
import com.example.yugioh.Constants.mainScenePath

class SceensNavigation(val navHostController: NavHostController) {
    val navigateToMainScene={

        navHostController.navigate(mainScenePath)
    }
    val navigateToCardScene={id:Int->
        navHostController.navigate("$cardSceneName/$id")
    }
}