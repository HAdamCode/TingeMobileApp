package com.example.tinge.presentation.navigation.specs

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.tinge.LocationUtility
import com.example.tinge.MainActivity
import com.example.tinge.R
import com.example.tinge.presentation.profile.TingeProfileScreen
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import kotlinx.coroutines.CoroutineScope

object ProfileScreenSpec : IScreenSpec {
    private const val LOG_TAG = "Tinge.ProfileScreenSpec"
    override val route = "profile"
    override val arguments: List<NamedNavArgument> = emptyList()
    override fun buildRoute(vararg args: String?) = route

    @Composable
    override fun Content(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry,
        coroutineScope: CoroutineScope,
        context: Context,
        mainActivity: MainActivity,
        locationUtility: LocationUtility,
        permissionLauncher: ActivityResultLauncher<Array<String>>
    ) {
        tingeViewModel.checkIfInDB()
        val person =
            tingeViewModel.currentUserState.collectAsStateWithLifecycle(context = coroutineScope.coroutineContext)

        person.value?.let { TingeProfileScreen(it) }

    }

    @Composable
    override fun TopAppBarActions(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ) {
        IconButton(onClick = { navController.navigate(route = ProfileEditScreenSpec.route) }) {
            Icon(
                imageVector = Icons.Filled.Edit, contentDescription = "Edit Personal Profile"
            )
        }
        IconButton(onClick = { navController.navigate(route = SettingsScreenSpec.route) }) {
            Icon(
                imageVector = Icons.Filled.Settings,
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
        TopAppBar(title = { Text("Your Profile") }, actions = {
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
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { navController.navigate(route = ListScreenSpec.route) }) {
                Icon(
                    painter = painterResource(R.drawable.explore),
                    contentDescription = "List Desc Placeholder!",
                    tint = Color(255, 121, 0)
                )
            }
            IconButton(onClick = {
                tingeViewModel.getChatPersonList()
                navController.navigate(route = ChatListScreenSpec.route)
            }) {
                Icon(
                    painter = painterResource(R.drawable.chaticon),
                    contentDescription = "Chat Desc Placeholder!",
                    tint = Color(25, 121, 100)
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile Desc Placeholder!",
                    tint = Color(55, 10, 100)
                )
            }
        }
    }
}