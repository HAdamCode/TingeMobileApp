package com.example.tinge.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tinge.data.TingePerson
import com.example.tinge.data.TingeRepo
import kotlin.math.floor

@Composable
fun TingeProfileScreen(person: TingePerson) {

    val feet = floor(person.height / 12.0).toInt()
    val inches = person.height % 12
    Card(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(start = 4.dp, end = 4.dp)) {
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
        }
    }
}

@Preview
@Composable
fun PreviewTingeProfileScreen() {
    TingeProfileScreen(person = TingeRepo.getInstance(LocalContext.current).persons.last())
}