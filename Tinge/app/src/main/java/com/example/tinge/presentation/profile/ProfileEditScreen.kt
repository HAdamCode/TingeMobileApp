package com.example.tinge.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tinge.data.TingePerson
import com.example.tinge.data.TingeRepo
import kotlin.math.floor

@Composable
fun ProfileEditScreen(person: TingePerson) {
    val feet = floor(person.height / 12.0).toInt()
    val inches = person.height % 12
    Column() {
        TextField(
            value = person.name,
            onValueChange = {},
            label = { Text(text = "Name") },
            modifier = Modifier.fillMaxWidth().padding(4.dp, 4.dp, 4.dp, 4.dp)
        )
        Row() {
            TextField(
                value = feet.toString(),
                onValueChange = {},
                label = { Text(text = "Feet") },
                modifier = Modifier.fillMaxWidth(0.5f).padding(4.dp, 4.dp, 4.dp, 4.dp)
            )
            TextField(
                value = inches.toString(),
                onValueChange = {},
                label = { Text(text = "Inches") },
                modifier = Modifier.fillMaxWidth().padding(4.dp, 4.dp, 4.dp, 4.dp)
            )
        }
        TextField(
            value = person.age.toString(),
            onValueChange = {},
            label = { Text(text = "Age") },
            modifier = Modifier.fillMaxWidth().padding(4.dp, 4.dp, 4.dp, 4.dp)
        )
    }
}

@Preview
@Composable
fun PreviewProfileEditScreen() {
    ProfileEditScreen(person = TingeRepo.getInstance(LocalContext.current).persons.last())
}