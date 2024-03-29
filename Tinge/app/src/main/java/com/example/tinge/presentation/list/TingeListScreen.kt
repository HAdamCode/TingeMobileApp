package com.example.tinge.presentation.list

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tinge.data.TingePerson
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import kotlinx.coroutines.delay
import java.util.Base64
import kotlin.math.floor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TingeListScreen(person: TingePerson, tingeViewModel: ITingeViewModel, rand: () -> Unit) {
    Log.d("LISTSCREEN", person.email.toString())
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

    val context = LocalContext.current
    val feet = floor(person.height / 12.0).toInt()
    val inches = person.height % 12
    var willDismissDirection: DismissDirection? by remember {
        mutableStateOf(null)
    }
    Card(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        val state = rememberDismissState(
            confirmStateChange = {
                if (willDismissDirection == DismissDirection.EndToStart) {
                    tingeViewModel.addMatch(person, true)
                    rand()
                    likeToast(context)
                } else if (willDismissDirection == DismissDirection.StartToEnd) {
                    tingeViewModel.addMatch(person, false)
                    rand()
                    dislikeToast(context)
                }
                false
            }
        )
        LaunchedEffect(key1 = Unit, block = {
            snapshotFlow { state.offset.value }
                .collect {
                    willDismissDirection =
                        when {
                            it > 250f -> {
                                DismissDirection.EndToStart
                            }

                            it < -250f -> {
                                DismissDirection.StartToEnd
                            }

                            else -> null
                        }
                }
        })
        SwipeToDismiss(
            dismissThresholds = {
                if (it == DismissDirection.StartToEnd) {
                    FractionalThreshold(100f)
                } else {
                    FractionalThreshold(100f)
                }
            },
            background = {

            },
            state = state,
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
                Row {
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (person.imageId != "") {
                        val image = loadImageFromBase64(person.imageId)
                        if (image != null) {
                            Image(image.asImageBitmap(), "image")
                        }

                    }
                }
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
                            IconButton(onClick = {
                                tingeViewModel.addMatch(person, false)
                                tingeViewModel.getRandomProfile()
                                dislikeToast(context)
                            }) {
                                Icon(
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
                            IconButton(onClick = {
                                tingeViewModel.addMatch(person, true)
                                tingeViewModel.getRandomProfile()
                                likeToast(context)
                            }) {
                                Icon(
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
}

fun likeToast(context: Context) {
    Toast.makeText(context, "Like button pressed", Toast.LENGTH_SHORT).show()
}

fun dislikeToast(context: Context) {
    Toast.makeText(context, "Dislike button pressed", Toast.LENGTH_SHORT).show()
}

//@Preview
//@Composable
//fun PreviewTingeProfileScreen() {
//    TingeListScreen(person = TingeRepo.getInstance(LocalContext.current).persons.first())
//}
//
//@Preview
//@Composable
//fun PreviewTingeProfileScreen2() {
//    TingeListScreen(person = TingeRepo.getInstance(LocalContext.current).persons.last())
//}