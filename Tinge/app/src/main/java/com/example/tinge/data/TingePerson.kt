package com.example.tinge.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "person")
data class TingePerson(
    val firstName: String,
    val lastName: String,
    val imageId: Int,
    var age: Int,
    val height: Int,
    val gender: String,
    @PrimaryKey
    val id: UUID = UUID.randomUUID()
)
