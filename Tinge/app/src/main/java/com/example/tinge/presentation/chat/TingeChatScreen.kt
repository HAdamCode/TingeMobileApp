package com.example.tinge.presentation.chat

import android.graphics.ColorSpace.Rgb
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tinge.R
import com.example.tinge.data.TingeMessages
import com.example.tinge.data.TingePerson
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun TingeChatScreen(
    person: TingePerson,
    messages: List<TingeMessages>,
    tingeViewModel: ITingeViewModel
) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxHeight(.91f)) {
        LazyColumn {
            items(messages) { message ->
                if (message.sender == FirebaseAuth.getInstance().currentUser?.email) {
                    Row(
                        modifier = Modifier
                            .background(color = Color.Gray)
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Card(
                            modifier = Modifier, shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = message.text,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .background(color = colorResource(id = R.color.orange_1))
                                    .padding(5.dp)
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .background(color = Color.Gray)
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Card(
                            modifier = Modifier, shape = RoundedCornerShape(5.dp)
                        ) {
                            Text(
                                text = message.text,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .background(color = Color.LightGray)
                                    .padding(5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentHeight(Alignment.Bottom)
    ) {
        Column(modifier = Modifier.fillMaxWidth(.75f).padding(end = 5.dp)) {
            TextField(
                value = text,
                onValueChange = { text = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    val userEmail = FirebaseAuth.getInstance().currentUser?.email
                    val message: TingeMessages
                    if (userEmail != null && person.email != null && text != "") {
                        message = TingeMessages(userEmail, person.email, text, Timestamp.now())
                        tingeViewModel.sendMessage(message)
                    }
                    tingeViewModel.getCurrentChatList(person)
                    focusManager.clearFocus()
                    text = ""
                }),
                shape = RoundedCornerShape(35.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Button(onClick = {
            val userEmail = FirebaseAuth.getInstance().currentUser?.email
            val message: TingeMessages
            if (userEmail != null && person.email != null && text != "") {
                message = TingeMessages(userEmail, person.email, text, Timestamp.now())
                tingeViewModel.sendMessage(message)
            }
            tingeViewModel.getCurrentChatList(person)
            focusManager.clearFocus()
            text = ""
        }) {
            Text(text = "Send")
        }
    }
}