package com.example.tinge.presentation.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument

object SettingsScreenSpec: IScreenSpec {
    private const val LOG_TAG = "Tinge.SettingsScreenSpec"
    override val route = "settings"
    override val arguments: List<NamedNavArgument> = emptyList()
    override fun buildRoute(vararg args: String?) = route

    @Composable
    override fun Content() {

    }
}