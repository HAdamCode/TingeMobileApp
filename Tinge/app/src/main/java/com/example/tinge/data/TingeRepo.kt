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

class TingeRepo private constructor(
    private val tingeDao: TingeDao,
    private val coroutineScope: CoroutineScope = GlobalScope
) {
    companion object {
        private const val LOG_TAG = "Tinge.TingeRepo"
        private var INSTANCE: TingeRepo? = null

        fun getInstance (context: Context): TingeRepo {
            var instance = INSTANCE
            if (instance == null) {
                val database = TingeDatabase.getInstance(context)
                instance = TingeRepo(database.tingeDao)
                INSTANCE = instance
            }
            return instance
        }
    }

    val persons: List<TingePerson>

    init {
        Log.d(LOG_TAG,"Initializing Repo List")
        val personsList = listOf(
            TingePerson(
                imageId = R.drawable.cute_blue_monsters_university_icon,
                firstName = "Adam",
                lastName = "Sandler",
                age = 20,
                height = 72,
                gender = "Male"
            ),
            TingePerson(
                imageId = R.drawable.monsters_university_character_randy_boggs_icon,
                firstName = "Randy",
                lastName = "Johnson",
                age = 23,
                height = 63,
                gender = "Non-binary"
            )
        )
        persons = personsList
    }
    fun getPersons(): Flow<List<TingePerson>> = tingeDao.getPersons()
    suspend fun getPerson(id: UUID): TingePerson? = tingeDao.getPersonById(id)
    fun addPerson(tingePerson: TingePerson) {
        coroutineScope.launch {
            tingeDao.addPerson(tingePerson)
        }
    }

    fun deletePerson(tingePerson: TingePerson) {
        coroutineScope.launch {
            tingeDao.deletePerson(tingePerson)
        }
    }
}