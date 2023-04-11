package com.example.tinge.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tinge.data.TingePerson

@Database(entities = [TingePerson::class], version = 1)
@TypeConverters(TingeTypeConverters::class)
abstract class TingeDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: TingeDatabase? = null
        fun getInstance(context: Context): TingeDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context, TingeDatabase::class.java,
                        "crime-database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    abstract val tingeDao: TingeDao
}