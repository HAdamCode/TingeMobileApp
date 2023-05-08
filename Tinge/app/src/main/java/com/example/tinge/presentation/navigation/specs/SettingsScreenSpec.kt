package com.example.tinge.presentation.navigation.specs

import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.tinge.LocationUtility
import com.example.tinge.MainActivity
import com.example.tinge.data.TingePerson
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
        context: Context,
        mainActivity: MainActivity,
        locationUtility: LocationUtility,
        permissionLauncher: ActivityResultLauncher<Array<String>>
    ) {
        tingeViewModel.checkIfInDB()
        val locationState = locationUtility
            .currentLocationStateFlow
            .collectAsStateWithLifecycle(context = coroutineScope.coroutineContext)

        val tingePerson = tingeViewModel
            .currentUserState
            .collectAsStateWithLifecycle(context = coroutineScope.coroutineContext)

        LaunchedEffect(locationState.value, tingePerson.value) {
            val location = locationState.value
            val person = tingePerson.value
            if (location != null && person != null) {
                val tPerson = TingePerson(
                    person.firstName,
                    person.lastName,
                    person.imageId,
                    person.age,
                    person.height,
                    person.gender,
                    person.email,
                    person.preference,
                    location.latitude,
                    location.longitude
                )
                tingeViewModel.updatePerson(tPerson, person.imageId)
                tingeViewModel.checkIfInDB()
            }
        }

        TingeSettingsScreen(
            locationUtility,
            mainActivity,
            permissionLauncher,
            tingeViewModel,
            coroutineScope,
            tingePerson.value,
            navController
        )
    }

    @Composable
    override fun TopAppBarActions(
        tingeViewModel: ITingeViewModel,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?,
        context: Context
    ) {
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
                Row {
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
        }, title = { Text("Settings") }, actions = {
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