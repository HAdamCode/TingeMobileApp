package com.example.tinge.presentation.navigation.specs

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.tinge.presentation.chat.TingeChatListScreen
import com.example.tinge.presentation.chat.TingeChatScreen
import com.example.tinge.presentation.list.TingeListScreen
import com.example.tinge.presentation.viewmodel.ITingeViewModel

object ChatScreenSpec: IScreenSpec {
    private const val LOG_TAG = "Tinge.ChatScreenSpec"
    override val route = "chat"
    override val arguments: List<NamedNavArgument> = emptyList()
    override fun buildRoute(vararg args: String?) = route

    @Composable
    override fun Content(tingeViewModel: ITingeViewModel,
                         navController: NavHostController,
                         navBackStackEntry: NavBackStackEntry,
                         context: Context) {
        val person = tingeViewModel.currentPersonState.collectAsState()
        person.value?.let { TingeChatScreen(it) }
    }

    @Composable
    override fun TopAppBarActions(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ){
        //Should have button to navigate to settings
        IconButton(onClick = { navController.navigate(route = SettingsScreenSpec.route) }) {
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
        }, title = { Text("Chat With: ") },
            actions = {
                ChatScreenSpec.TopAppBarActions(
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
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ){
            //Should have button to navigate to settings
            IconButton(onClick = { navController.navigate(route = ListScreenSpec.route) }) {
                Icon(
                    //PLACEHOLDER ICON
                    imageVector = Icons.Filled.Call,
                    contentDescription = "List Desc Placeholder!"
                )
            }
            //Should have button to navigate to settings
            IconButton(onClick = { }) {
                Icon(
                    //PLACEHOLDER ICON
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = "Chat Desc Placeholder!"
                )
            }
            //Should have button to navigate to settings
            IconButton(onClick = { navController.navigate(route = ProfileScreenSpec.route) }) {
                Icon(
                    //PLACEHOLDER ICON
                    imageVector = Icons.Filled.AccountBox,
                    contentDescription = "Profile Desc Placeholder!"
                )
            }
        }
    }
}