package com.example.tinge.presentation.navigation

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tinge.LocationUtility
import com.example.tinge.MainActivity
import com.example.tinge.presentation.navigation.specs.IScreenSpec
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import kotlinx.coroutines.CoroutineScope


@Composable
fun TingeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    tingeViewModel: ITingeViewModel,
    coroutineScope: CoroutineScope,
    context: Context,
    mainActivity: MainActivity,
    locationUtility: LocationUtility,
    permissionLauncher: ActivityResultLauncher<Array<String>>
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = IScreenSpec.root
    ) {
        navigation(
            route = IScreenSpec.root, startDestination = IScreenSpec.startDestination
        ) {
            IScreenSpec.allScreens.forEach { (_, screen) ->
                if (screen != null) {
                    composable(
                        route = screen.route, arguments = screen.arguments
                    ) { navBackStackEntry ->
                        screen.Content(
                            tingeViewModel = tingeViewModel,
                            navController = navController,
                            navBackStackEntry = navBackStackEntry,
                            coroutineScope = coroutineScope,
                            context = context,
                            mainActivity = mainActivity,
                            locationUtility = locationUtility,
                            permissionLauncher = permissionLauncher
                        )
                    }
                }
            }
        }
    }
}