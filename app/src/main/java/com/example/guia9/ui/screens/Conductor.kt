package com.example.guia9.ui.screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.guia9.ui.utils.obtemerWindowSizeClass

@Composable
fun Conductor(){
    val windowClass = obtemerWindowSizeClass()
    when (windowClass.widthSizeClass){
        WindowWidthSizeClass.Compact -> HomeScreenCompacta()
        WindowWidthSizeClass.Medium -> HomeScreenCompacta()
        WindowWidthSizeClass.Expanded -> HomeScreenCompacta()

    }
}