package com.example.tinge.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tinge.presentation.navigation.specs.IScreenSpec
import com.example.tinge.presentation.viewmodel.ITingeViewModel

@Composable
fun TingeTopBar(
    tingeViewModel: ITingeViewModel,
    navController: NavHostController,
    context: Context
){
    val navBackStackEntryState = navController.currentBackStackEntryAsState()
    IScreenSpec.TopBar(
        tingeViewModel = tingeViewModel,
        navController =navController ,
        navBackStackEntry = navBackStackEntryState.value,
        context = context
    )
}

