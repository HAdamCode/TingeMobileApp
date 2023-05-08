package com.example.tinge.presentation.settings

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tinge.LocationUtility
import com.example.tinge.MainActivity

@Composable
fun TingeSettingsScreen(locationUtility: LocationUtility,mainActivity: MainActivity,permissionLauncher: ActivityResultLauncher<Array<String>>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Update to current Location: ", modifier = Modifier.weight(0.7f))
        Button(onClick = {locationUtility.checkPermissionAndGetLocation(mainActivity,permissionLauncher)}) {
            Text(text = "Get Location")
        }
    }
}


