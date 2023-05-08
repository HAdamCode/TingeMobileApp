package com.example.tinge.presentation.settings

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tinge.LocationUtility
import com.example.tinge.MainActivity
import com.example.tinge.data.TingePerson
import com.example.tinge.presentation.navigation.specs.ChatScreenSpec
import com.example.tinge.presentation.navigation.specs.SettingsScreenSpec
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun TingeSettingsScreen(
    locationUtility: LocationUtility,
    mainActivity: MainActivity,
    permissionLauncher: ActivityResultLauncher<Array<String>>,
    tingeViewModel: ITingeViewModel,
    coroutineScope: CoroutineScope,
    tingePerson: TingePerson?,
    navController: NavHostController
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
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
        if (tingePerson != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(text = "latitude", modifier = Modifier.fillMaxWidth(.5f))
                Text(text = tingePerson.lat.toString(), modifier = Modifier.fillMaxWidth())
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(text = "longitude", modifier = Modifier.fillMaxWidth(.5f))
                Text(text = tingePerson.lon.toString(), modifier = Modifier.fillMaxWidth())
            }
        }
    }
}


