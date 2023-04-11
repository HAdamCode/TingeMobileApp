package com.example.tinge.data.database

import androidx.room.TypeConverter
import java.util.*

class TingeTypeConverters {
    @TypeConverter
    fun fromUUID(uuid: UUID?) = uuid?.toString()

    @TypeConverter
    fun toUUID(uuid: String?) = UUID.fromString(uuid)
}