package com.example.tinge.presentation.navigation.specs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.tinge.LocationUtility
import com.example.tinge.MainActivity
import com.example.tinge.NetworkConnectionUtil
import com.example.tinge.presentation.chat.TingeChatScreen
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import kotlinx.coroutines.CoroutineScope
import java.util.Base64

object ChatScreenSpec : IScreenSpec {
    private const val LOG_TAG = "Tinge.ChatScreenSpec"
    override val route = "chat"
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
        val canSend = NetworkConnectionUtil.isNetworkAvailableAndConnected(context)
        val person =
            tingeViewModel.currentPersonChatState.collectAsStateWithLifecycle(context = coroutineScope.coroutineContext)
        val messages =
            tingeViewModel.currentMessagesListState.collectAsStateWithLifecycle(context = coroutineScope.coroutineContext)
        person.value?.let { TingeChatScreen(it, messages.value, tingeViewModel, canSend) }
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
        fun loadImageFromBase64(base64String: String): Bitmap? {
            try {
                Log.d("Decoding: ", base64String.length.toString())
                val decodedBytes = Base64.getDecoder().decode(base64String)
                return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            } catch (e: IllegalArgumentException) {
                // Invalid Base64 string
                e.printStackTrace()
            }
            return null
        }
        val name =
            tingeViewModel.currentPersonChatState.collectAsState().value?.firstName + " " + tingeViewModel.currentPersonChatState.collectAsState().value?.lastName
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
        }, title = {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Chat With: $name", modifier = Modifier.fillMaxWidth(.88f).padding(top = 3.dp))
                val person = tingeViewModel.currentPersonChatState.collectAsState().value
                if (person != null && person.imageId != "") {
                    val image = loadImageFromBase64(person.imageId)
                    if (image != null) {
                        Image(image.asImageBitmap(), "image", modifier = Modifier.size(40.dp).fillMaxWidth())
                    }

                }
            }
                   }, actions = {
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