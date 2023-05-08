package com.example.tinge.presentation.chat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tinge.data.TingePerson
import com.example.tinge.presentation.navigation.specs.ChatScreenSpec
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import java.util.Base64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TingeChatListScreen(
    tingeViewModel: ITingeViewModel,
    navController: NavHostController,
    people: List<TingePerson>,
    canSend: Boolean
) {
    fun loadImageFromBase64(base64String: String): Bitmap? {
        try {
            Log.d("Decoding: ", base64String.length.toString())
            val decodedBytes = Base64.getDecoder().decode(base64String)
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        return null
    }
    Column {
        people.forEach { person ->
            Card(modifier = Modifier.padding(12.dp), enabled = canSend, onClick = {
                tingeViewModel.getCurrentChatList(person)
                navController.navigate(route = ChatScreenSpec.route)
            }) {
                Row {
                    if (person.imageId != "") {
                        val image = loadImageFromBase64(person.imageId)
                        if (image != null) {
                            Image(
                                image.asImageBitmap(), "image", modifier = Modifier.size(100.dp)
                            )
                        }

                    }
                    Text(
                        text = person.firstName + ' ' + person.lastName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 28.dp, start = 22.dp),
                        style = TextStyle(fontSize = 25.sp)
                    )
                }
            }
        }
    }
}