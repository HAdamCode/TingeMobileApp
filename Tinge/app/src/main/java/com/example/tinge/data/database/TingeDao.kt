package com.example.tinge.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tinge.data.TingePerson
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TingeDao {

    @Insert
    fun addPerson(tingePerson: TingePerson)

    @Query("SELECT * FROM person")
    fun getPersons(): Flow<List<TingePerson>>

    @Query("SELECT * FROM person WHERE id=(:id)")
    suspend fun getPersonById(id: UUID): TingePerson?

    @Delete
    fun deletePerson(tingePerson: TingePerson)
}