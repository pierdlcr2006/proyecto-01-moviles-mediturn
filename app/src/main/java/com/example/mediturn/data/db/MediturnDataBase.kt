package com.example.mediturn.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mediturn.data.model.*

@Database(
    entities = [
        Patient::class,
        DoctorEntity::class,
        EspecialityEntity::class,
        Appointment::class,
        Notification::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MediturnDataBase : RoomDatabase() {

    
    companion object {
        @Volatile
        private var INSTANCE: MediturnDataBase? = null
        
        fun getDatabase(context: Context): MediturnDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MediturnDataBase::class.java,
                    "mediturn_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}