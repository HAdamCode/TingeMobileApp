package com.example.tinge.presentation.navigation.specs

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.tinge.presentation.settings.TingeSettingsScreen
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import kotlinx.coroutines.CoroutineScope

object SettingsScreenSpec : IScreenSpec {
    private const val LOG_TAG = "Tinge.SettingsScreenSpec"
    override val route = "settings"
    override val arguments: List<NamedNavArgument> = emptyList()
    override fun buildRoute(vararg args: String?) = route

    @Composable
    override fun Content(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        coroutineScope: CoroutineScope,
        context: Context
    ) {
        TingeSettingsScreen()
    }

    @Composable
    override fun TopAppBarActions(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ) {
        //Should have button to navigate to settings
        IconButton(onClick = { navController.navigate(route = route) }) {
            Icon(
                //PLACEHOLDER ICON
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "Settings Desc Placeholder!"
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun TopAppBarContent(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ) {
        TopAppBar(navigationIcon = if (navController.previousBackStackEntry != null) {
            {
                Row(
                    //verticalAlignment = Alignment.CenterVertically,
                    //horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Placeholder"
                        )
                    }
                }
            }
        } else {
            { }
        }, title = { Text("Settings") },
            actions = {
                TopAppBarActions(
                    tingeViewModel = tingeViewModel,
                    navController = navController,
                    navBackStackEntry = navBackStackEntry,
                    context = context
                )
            })
    }

    @Composable
    override fun BottomAppBarActions(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ) {
    }
}