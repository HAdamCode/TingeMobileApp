package com.example.tinge.presentation.settings

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tinge.LocationUtility
import com.example.tinge.MainActivity
import com.example.tinge.data.TingePerson
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun TingeSettingsScreen(
    locationUtility: LocationUtility,
    mainActivity: MainActivity,
    permissionLauncher: ActivityResultLauncher<Array<String>>,
    person: TingePerson?,
    tingeViewModel: ITingeViewModel,
    coroutineScope: CoroutineScope,
) {
    tingeViewModel.checkIfInDB()
    val locationState = locationUtility
        .currentLocationStateFlow
        .collectAsStateWithLifecycle(context = coroutineScope.coroutineContext)

    val tingePerson = tingeViewModel
        .currentUserState
        .collectAsStateWithLifecycle(context = coroutineScope.coroutineContext)

    LaunchedEffect(locationState.value, tingePerson.value) {
        val location = locationState.value
        val person = tingePerson.value
        if (location != null && person != null) {
            val tingePerson = TingePerson(
                person.firstName,
                person.lastName,
                person.imageId,
                person.age,
                person.height,
                person.gender,
                person.email,
                person.preference,
                location.latitude,
                location.longitude
            )
            tingeViewModel.updatePerson(tingePerson, person.imageId)
            tingeViewModel.checkIfInDB()
        }
    }
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Text(
                text = "Update to current Location: ",
                modifier = Modifier
                    .weight(0.7f)
                    .padding(top = 10.dp)
            )
            Button(onClick = {
                locationUtility.checkPermissionAndGetLocation(
                    mainActivity,
                    permissionLauncher
                )
            }) {
                Text(text = "Get Location")
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Text(text = "latitude", modifier = Modifier.fillMaxWidth(.5f))
            if (person != null) {
                Text(text = person.lat.toString(), modifier = Modifier.fillMaxWidth())
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Text(text = "longitude", modifier = Modifier.fillMaxWidth(.5f))
            if (person != null) {
                Text(text = person.lon.toString(), modifier = Modifier.fillMaxWidth())
            }
        }
    }
}


