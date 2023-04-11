package com.example.tinge.presentation.list

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tinge.data.TingePerson
import com.example.tinge.data.TingeRepo
import kotlin.math.floor

@Composable
fun TingeListScreen(person: TingePerson) {
    val context = LocalContext.current
    val feet = floor(person.height / 12.0).toInt()
    val inches = person.height % 12
    Card(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .padding(start = 4.dp, end = 4.dp)
                .weight(1f)
        ) {
            Text(
                text = person.firstName + ' ' + person.lastName,
                fontSize = 34.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Row() {
                Text(
                    text = " Age: ${person.age}  | ",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = " Height: $feet' $inches\"  | ",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = " Gender: ${person.gender}",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Image(
                painter = painterResource(
                    id =
                    person.imageId
                ),
                contentDescription = "",
                Modifier
                    .fillMaxWidth()
                    .size(350.dp)
                    .fillMaxHeight()
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(onClick = { dislikeToast(context) }) {
                            Icon(
                                //PLACEHOLDER ICON
                                //imageVector = Icons.Filled.Delete,
                                painter = painterResource(com.example.tinge.R.drawable.dislikebutton),
                                contentDescription = "List Desc Placeholder!",
                                tint = Color.Black
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IconButton(onClick = { likeToast(context) }) {
                            Icon(
                                //PLACEHOLDER ICON
                                //imageVector = Icons.Filled.CheckCircle,
                                painter = painterResource(com.example.tinge.R.drawable.likebutton),
                                contentDescription = "List Desc Placeholder!",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }

        }
    }
}

fun likeToast(context: Context) {
    Toast.makeText(context, "Like button pressed", Toast.LENGTH_SHORT).show()
}

fun dislikeToast(context: Context) {
    Toast.makeText(context, "Dislike button pressed", Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
fun PreviewTingeProfileScreen() {
    TingeListScreen(person = TingeRepo.getInstance(LocalContext.current).persons.first())
}

@Preview
@Composable
fun PreviewTingeProfileScreen2() {
    TingeListScreen(person = TingeRepo.getInstance(LocalContext.current).persons.last())
}