package com.example.tinge.presentation.navigation.specs

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument

object ProfileScreenSpec: IScreenSpec {
    private const val LOG_TAG = "Tinge.ProfileScreenSpec"
    override val route = "profile"
    override val arguments: List<NamedNavArgument> = emptyList()
    override fun buildRoute(vararg args: String?) = route

    @Composable
    override fun Content() {

    }
}