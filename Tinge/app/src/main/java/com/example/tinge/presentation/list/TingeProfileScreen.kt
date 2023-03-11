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

@Composable
fun TingeProfileScreen(person: TingePerson) {

    Card() {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp, start = 4.dp, end = 4.dp)
        )
        {
            Image(
                painter = painterResource(
                    id =
                    person.imageId
                ),
                contentDescription = ""
            )
            Column(modifier = Modifier.padding(4.dp))
            {
                Text(
                    text = person.name,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTingeProfileScreen() {
    TingeProfileScreen(person = TingeRepo.getInstance(LocalContext.current).persons.first())
}