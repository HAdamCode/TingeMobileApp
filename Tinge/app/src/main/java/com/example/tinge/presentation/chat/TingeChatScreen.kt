package com.example.tinge.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tinge.data.TingeMessages
import com.example.tinge.data.TingePerson
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TingeChatScreen(person: TingePerson, messages: List<TingeMessages>, tingeViewModel: ITingeViewModel) {
    var text by remember { mutableStateOf("") }
    LazyColumn {
        items(messages) { message ->
            Text("${message.sender}: ${message.text}")
        }
        // Add a TextField and Button for sending new messages
    }
    Row(modifier = Modifier
        .fillMaxHeight()
        .wrapContentHeight(Alignment.Bottom)) {
        TextField(
            value = text,
            onValueChange = { text = it }
        )
        Button(onClick = {
            val userEmail = FirebaseAuth.getInstance().currentUser?.email
            val message: TingeMessages
            if (userEmail != null && person.email != null) {
                message = TingeMessages(userEmail, person.email, text, Timestamp.now())
                tingeViewModel.sendMessage(message)
            }
            tingeViewModel.getCurrentChatList(person)
        }) {
            Text(text = "Send")
        }
    }

}