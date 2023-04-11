package com.example.tinge.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tinge.presentation.navigation.specs.IScreenSpec
import com.example.tinge.presentation.viewmodel.ITingeViewModel


@Composable
fun TingeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    tingeViewModel: ITingeViewModel,
    context: Context
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = IScreenSpec.root
    ) {
        navigation(
            route = IScreenSpec.root,
            startDestination = IScreenSpec.startDestination
        ) {
            IScreenSpec.allScreens.forEach { (_, screen) ->
                if (screen != null) {
                    composable(
                        route = screen.route,
                        arguments = screen.arguments
                    ) { navBackStackEntry ->
                        screen.Content(
                            tingeViewModel = tingeViewModel,
                            navController = navController,
                            navBackStackEntry = navBackStackEntry,
                            context = context
                        )
                    }
                }
            }
        }
    }
}