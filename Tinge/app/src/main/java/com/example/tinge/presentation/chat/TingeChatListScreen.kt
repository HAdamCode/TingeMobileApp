package com.example.tinge.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tinge.presentation.navigation.specs.ChatScreenSpec
//import com.example.tinge.presentation.navigation.specs.ChatScreenSpec
import com.example.tinge.presentation.viewmodel.ITingeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TingeChatListScreen(tingeViewModel: ITingeViewModel, navController: NavHostController) {
    val person = tingeViewModel.currentPersonState.collectAsState().value
    if (person != null) {
        Card(
            modifier = Modifier.padding(8.dp),
            onClick = { navController.navigate(route = ChatScreenSpec.route) }) {
            Row() {
                Image(
                    painter = painterResource(
                        id = person.imageId
                    ),
                    contentDescription = "",
                    Modifier
                        .size(50.dp)
                )
                Text(text = person.name, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp))
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewTingeChatListScreen() {
//
//    TingeChatListScreen()
//}