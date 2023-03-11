package com.example.tinge.data

import android.content.Context
import android.util.Log
import com.example.tinge.R
import com.example.tinge.data.database.TingeDao
import com.example.tinge.data.database.TingeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*

object TingeRepo {
    public val persons = listOf(
        TingePerson(
            imageId = R.drawable.cute_blue_monsters_university_icon,
            name = "Sandler"
        ),
        TingePerson(
            imageId = R.drawable.monsters_university_character_randy_boggs_icon,
            name = "Randy"
        )
    )
}