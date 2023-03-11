package com.example.tinge.presentation.navigation.specs

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.tinge.presentation.settings.TingeSettingsScreen
import com.example.tinge.presentation.viewmodel.ITingeViewModel

object SettingsScreenSpec: IScreenSpec {
    private const val LOG_TAG = "Tinge.SettingsScreenSpec"
    override val route = "settings"
    override val arguments: List<NamedNavArgument> = emptyList()
    override fun buildRoute(vararg args: String?) = route

    @Composable
    override fun Content(tingeViewModel: ITingeViewModel,
                         navController: NavHostController,
                         navBackStackEntry: NavBackStackEntry,
                         context: Context) {
        TingeSettingsScreen()
    }

    @Composable
    override fun TopAppBarActions(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ){
        //Should have button to navigate to settings
        IconButton(onClick = { navController.navigate(route = route) }) {
            Icon(
                //PLACEHOLDER ICON
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "Settings Desc Placeholder!"
            )
        }
    }

    @Composable
    override fun BottomAppBarActions(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ) {
//        //Should have button to navigate to settings
//        IconButton(onClick = { navController.navigate(route = ListScreenSpec.route) }) {
//            Icon(
//                //PLACEHOLDER ICON
//                imageVector = Icons.Filled.AddCircle,
//                contentDescription = "List Desc Placeholder!"
//            )
//        }
//        //Should have button to navigate to settings
//        IconButton(onClick = { navController.navigate(route = ChatScreenSpec.route) }) {
//            Icon(
//                //PLACEHOLDER ICON
//                imageVector = Icons.Filled.AddCircle,
//                contentDescription = "Chat Desc Placeholder!"
//            )
//        }
//        //Should have button to navigate to settings
//        IconButton(onClick = { navController.navigate(route = ProfileScreenSpec.route) }) {
//            Icon(
//                //PLACEHOLDER ICON
//                imageVector = Icons.Filled.AddCircle,
//                contentDescription = "Profile Desc Placeholder!"
//            )
//        }
    }
}