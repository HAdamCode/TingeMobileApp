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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tinge.presentation.navigation.specs.ChatScreenSpec
//import com.example.tinge.presentation.navigation.specs.ChatScreenSpec
import com.example.tinge.presentation.viewmodel.ITingeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TingeChatListScreen(tingeViewModel: ITingeViewModel, navController: NavHostController) {
    val people = tingeViewModel.personListState.collectAsState().value
    Column() {
        people.forEach { person ->
            Card(
                modifier = Modifier.padding(12.dp),
                onClick = { navController.navigate(route = ChatScreenSpec.route) }) {
                Row() {
                    Image(
                        painter = painterResource(
                            id = person.imageId
                        ),
                        contentDescription = "",
                        Modifier
                            .size(90.dp)
                    )
                    Text(
                        text = person.firstName + ' ' + person.lastName, modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 28.dp, start = 22.dp),
                        style = TextStyle(fontSize = 25.sp)
                    )
                }
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