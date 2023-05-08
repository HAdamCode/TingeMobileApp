package com.example.tinge.presentation.profile

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tinge.data.TingePerson
import java.util.Base64
import kotlin.math.floor

@Composable
fun TingeProfileScreen(person: TingePerson) {
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

    val feet = floor(person.height / 12.0).toInt()
    val inches = person.height % 12
    Card(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Log.d("ProfileScreen", person.imageId.length.toString())
        Column(Modifier.padding(start = 4.dp, end = 4.dp)) {
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
        }
    }
}