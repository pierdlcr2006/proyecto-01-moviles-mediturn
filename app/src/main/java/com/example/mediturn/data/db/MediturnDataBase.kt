package com.example.mediturn.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mediturn.data.dao.AppointmentDao
import com.example.mediturn.data.dao.DoctorDao
import com.example.mediturn.data.dao.NotificationDao
import com.example.mediturn.data.dao.PatientDao
import com.example.mediturn.data.dao.SpecialtyDao
import com.example.mediturn.data.model.Appointment
import com.example.mediturn.data.model.DoctorEntity
import com.example.mediturn.data.model.EspecialityEntity
import com.example.mediturn.data.model.Notification
import com.example.mediturn.data.model.PatientEntity
import com.example.mediturn.data.model.converters.MediturnTypeConverters

@Database(
    entities = [
        PatientEntity::class,
        DoctorEntity::class,
        EspecialityEntity::class,
        Appointment::class,
        Notification::class
    ],
    version = 2,  // Incrementado para agregar campos consultationType, reason, rescheduleCount
    exportSchema = false
)
@TypeConverters(MediturnTypeConverters::class)
abstract class MediturnDataBase : RoomDatabase() {

    abstract fun patientDao(): PatientDao
    abstract fun doctorDao(): DoctorDao
    abstract fun specialtyDao(): SpecialtyDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun notificationDao(): NotificationDao
    
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
