package com.example.tinge.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tinge.data.TingePerson

@Composable
fun TingeChatScreen(person: TingePerson) {
    var text by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentHeight(Alignment.Bottom),
        value = text,
        onValueChange = { text = it }
    )
}