package com.example.tinge.data

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.tinge.R

class TingeRepo private constructor(
    context: Context?
) {
    companion object {
        private const val LOG_TAG = "Tinge.TingeRepo"
        private var INSTANCE: TingeRepo? = null

        fun getInstance(context: Context? = null): TingeRepo {
            var instance = INSTANCE
            if (instance == null) {
                instance = TingeRepo(context)
                INSTANCE = instance
            }
            return instance
        }
    }

    val persons: List<TingePerson>

    init {
        Log.d(LOG_TAG, "Initializing Repo List")
        val personsList = listOf(
            TingePerson(
                imageId = "",
                firstName = "Adam",
                lastName = "Sandler",
                age = 20,
                height = 72,
                gender = "Male",
                email = "",
                preference = ""
            ),
            TingePerson(
                imageId = "",
                firstName = "Randy",
                lastName = "Johnson",
                age = 23,
                height = 63,
                gender = "Non-binary",
                email = "",
                preference = ""
            )
        )
        persons = personsList
    }
}