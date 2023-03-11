package com.example.tinge.presentation.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.tinge.presentation.viewmodel.TingeViewModel

@Composable
fun TingeSettingsScreen () {
    Text(text = "This is the Settings Screen")
}


@Preview (showBackground = true)
@Composable
fun TingeSettingsScreenPreview() {
    TingeSettingsScreen()
}