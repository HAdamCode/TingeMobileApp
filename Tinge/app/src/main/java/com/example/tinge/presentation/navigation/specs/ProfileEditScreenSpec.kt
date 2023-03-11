package com.example.tinge.presentation.navigation.specs

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.tinge.presentation.profile.ProfileEditScreen
import com.example.tinge.presentation.profile.TingeProfileScreen
import com.example.tinge.presentation.viewmodel.ITingeViewModel

object ProfileEditScreenSpec: IScreenSpec {
    private const val LOG_TAG = "Tinge.ProfileEditScreenSpec"
    override val route = "profileEdit"
    override val arguments: List<NamedNavArgument> = emptyList()

    override fun buildRoute(vararg args: String?) = ProfileScreenSpec.route

    @Composable
    override fun Content(tingeViewModel: ITingeViewModel,
                         navController: NavHostController,
                         navBackStackEntry: NavBackStackEntry,
                         context: Context
    ) {
        val person = tingeViewModel.currentPersonState.collectAsState()
        person.value?.let { ProfileEditScreen(it) }
    }

    @Composable
    override fun TopAppBarActions(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ){
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
        ) {
            IconButton(onClick = { navController.navigate(route = ListScreenSpec.route) }) {
                Icon(
                    //PLACEHOLDER ICON
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Save update"
                )
            }
        }
    }
}