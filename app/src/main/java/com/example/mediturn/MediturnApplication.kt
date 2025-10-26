package com.example.mediturn

import android.app.Application
import com.example.mediturn.data.db.MediturnDataBase
import com.example.mediturn.data.repository.MediturnRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MediturnApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    lateinit var repository: MediturnRepository
        private set

    override fun onCreate() {
        super.onCreate()
        val database = MediturnDataBase.getDatabase(this)
        repository = MediturnRepository(
            patientDao = database.patientDao(),
            doctorDao = database.doctorDao(),
            specialtyDao = database.specialtyDao(),
            appointmentDao = database.appointmentDao(),
            notificationDao = database.notificationDao()
        )

        applicationScope.launch {
            repository.seedDatabaseIfEmpty()
        }
    }
}
