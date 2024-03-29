package com.example.tinge.presentation.navigation.specs

import android.content.Context
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.tinge.LocationUtility
import com.example.tinge.MainActivity
import com.example.tinge.presentation.profile.ProfileEditScreen
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import kotlinx.coroutines.CoroutineScope

object ProfileEditScreenSpec : IScreenSpec, Fragment() {
    private const val LOG_TAG = "Tinge.ProfileEditScreenSpec"
    override val route = "profileEdit"
    override val arguments: List<NamedNavArgument> = emptyList()

    override fun buildRoute(vararg args: String?) = ProfileScreenSpec.route

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
        fun launchImagePickerFromScreen() {
            mainActivity.launchImagePicker()
        }

        tingeViewModel.checkIfInDB()
        val person =
            tingeViewModel.currentUserState.collectAsStateWithLifecycle(context = coroutineScope.coroutineContext)

        person.value?.let {
            ProfileEditScreen(
                it, tingeViewModel, navController, context
            ) { launchImagePickerFromScreen() }
        }
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
        }, title = { Text("Edit Your Profile") }, actions = {
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

    fun SaveToast(context: Context) {
        Toast.makeText(context, "Update pressed", Toast.LENGTH_SHORT).show()
    }
}