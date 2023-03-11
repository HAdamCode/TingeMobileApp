package com.example.tinge.presentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tinge.R
import com.example.tinge.data.TingePerson
import com.example.tinge.data.TingeRepo
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import kotlin.math.floor

@Composable
fun TingeProfileScreen(person: TingePerson) {

    val feet = floor(person.height/12.0).toInt()
    val inches = person.height%12
    Card() {
        Column(Modifier.padding(start = 4.dp, end = 4.dp)) {
                Text(
                    text = person.name,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Row() {
                    Text(
                        text = "Age: ${person.age}",
                        fontSize = 4.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = " Height: $feet' $inches\"",
                        fontSize = 4.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = " Gender: ${person.gender}",
                        fontSize = 4.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Image(
                    painter = painterResource(
                        id =
                        person.imageId
                    ),
                    contentDescription = ""
                )
            }
        }

}

@Preview
@Composable
fun PreviewTingeProfileScreen() {
    TingeProfileScreen(person = TingeRepo.getInstance(LocalContext.current).persons.first())
}

@Preview
@Composable
fun PreviewTingeProfileScreen2() {
    TingeProfileScreen(person = TingeRepo.getInstance(LocalContext.current).persons.first())
}