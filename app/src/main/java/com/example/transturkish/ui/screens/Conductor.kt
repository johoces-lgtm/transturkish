package com.example.transturkish.ui.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.transturkish.ui.utils.obtemerWindowSizeClass

@Composable
fun Conductor(){
    val windowClass = obtemerWindowSizeClass()
    when (windowClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> HomeScreenCompacta()
        WindowWidthSizeClass.Medium -> HomeScreenCompacta()
        WindowWidthSizeClass.Expanded -> HomeScreenCompacta()

    }
}