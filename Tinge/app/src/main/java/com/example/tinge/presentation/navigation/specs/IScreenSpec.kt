package com.example.tinge.presentation.navigation.specs

import android.content.Context
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.tinge.presentation.viewmodel.ITingeViewModel

sealed interface IScreenSpec {
    companion object{
        private const val LOG_TAG = "Tinge.IScreenSpec"

        val allScreens = IScreenSpec::class.sealedSubclasses.associate {
            Log.d(LOG_TAG,"allScreens: mapping route \"${it.objectInstance?.route ?: ""}\" to object \"${it.objectInstance}\"")
            it.objectInstance?.route to it.objectInstance
        }
        const val root = "tinge"
        val startDestination = ListScreenSpec.route
        @Composable
        fun TopBar(
            tingeViewModel: ITingeViewModel,
            navController: NavHostController,
            navBackStackEntry: NavBackStackEntry?,
            context: Context
        ) {
            val route = navBackStackEntry?.destination?.route ?: ""
            allScreens[route]?.TopAppBarContent(tingeViewModel, navController, navBackStackEntry, context)
        }

        @Composable
        fun BottomBar(
            tingeViewModel: ITingeViewModel,
            navController: NavHostController,
            navBackStackEntry: NavBackStackEntry?,
            context: Context
        ) {
            val route = navBackStackEntry?.destination?.route ?: ""
            allScreens[route]?.BottomAppBarContent(tingeViewModel, navController, navBackStackEntry, context)
        }
    }
    val route: String
    val arguments: List<NamedNavArgument>
    fun buildRoute(vararg args: String?): String

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBarContent(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ){
        TopAppBar(navigationIcon = if (navController.previousBackStackEntry != null) {
            {
                Row(
                    //verticalAlignment = Alignment.CenterVertically,
                    //horizontalArrangement = Arrangement.Center
                ){
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
        }, title = { Text("Top Bar") },
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
    fun TopAppBarActions(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun BottomAppBarContent(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ){
        TopAppBar(title = {  },
            actions = {
                BottomAppBarActions(
                    tingeViewModel = tingeViewModel,
                    navController = navController,
                    navBackStackEntry = navBackStackEntry,
                    context = context
                )
            })
    }

    @Composable
    fun BottomAppBarActions(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    )

    @Composable
    fun Content(tingeViewModel: ITingeViewModel,
                navController: NavHostController,
                navBackStackEntry: NavBackStackEntry,
                context: Context)

}