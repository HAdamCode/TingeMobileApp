package com.example.tinge.presentation.navigation.specs

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument

sealed interface IScreenSpec {
    companion object{
        private const val LOG_TAG = "Tinge.IScreenSpec"

        val allScreens = IScreenSpec::class.sealedSubclasses.associate {
            Log.d(LOG_TAG,"allScreens: mapping route \"${it.objectInstance?.route ?: ""}\" to object \"${it.objectInstance}\"")
            it.objectInstance?.route to it.objectInstance
        }
        const val root = "tinge"
        val startDestination = ListScreenSpec.route
    }
    val route: String
    val arguments: List<NamedNavArgument>
    fun buildRoute(vararg args: String?): String

    @Composable
    fun Content()

}