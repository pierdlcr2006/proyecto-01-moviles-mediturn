package com.example.mediturn.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mediturn.data.model.Appointment
import com.example.mediturn.data.model.AppointmentStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {

    @Query("SELECT * FROM appointments ORDER BY dateTime DESC")
    fun observeAppointments(): Flow<List<Appointment>>

    @Query("SELECT * FROM appointments WHERE id = :appointmentId LIMIT 1")
    suspend fun getById(appointmentId: Long): Appointment?

    @Query("SELECT * FROM appointments WHERE id = :appointmentId LIMIT 1")
    fun observeById(appointmentId: Long): Flow<Appointment?>

    @Query(
        "SELECT * FROM appointments " +
            "WHERE patientId = :patientId " +
            "ORDER BY dateTime DESC"
    )
    fun observeByPatient(patientId: Long): Flow<List<Appointment>>

    @Query(
        "SELECT * FROM appointments " +
            "WHERE doctorId = :doctorId " +
            "ORDER BY dateTime DESC"
    )
    fun observeByDoctor(doctorId: Long): Flow<List<Appointment>>

    @Query(
        "SELECT * FROM appointments " +
            "WHERE patientId = :patientId AND isPast = 0 " +
            "ORDER BY dateTime ASC"
    )
    fun observeUpcomingByPatient(patientId: Long): Flow<List<Appointment>>

    @Query(
        "SELECT * FROM appointments " +
            "WHERE patientId = :patientId AND isPast = 1 " +
            "ORDER BY dateTime DESC"
    )
    fun observePastByPatient(patientId: Long): Flow<List<Appointment>>

    @Query(
        "SELECT * FROM appointments " +
            "WHERE doctorId = :doctorId AND isPast = 0 " +
            "ORDER BY dateTime ASC"
    )
    fun observeUpcomingByDoctor(doctorId: Long): Flow<List<Appointment>>

    @Query(
        "UPDATE appointments SET status = :status, isPast = :isPast " +
            "WHERE id = :appointmentId"
    )
    suspend fun updateStatus(
        appointmentId: Long,
        status: AppointmentStatus,
        isPast: Boolean
    )

    @Query(
        "UPDATE appointments SET dateTime = :newDateTimeMillis, " +
            "rescheduleCount = rescheduleCount + 1, " +
            "status = 'RESCHEDULED' " +
            "WHERE id = :appointmentId"
    )
    suspend fun updateDateTime(
        appointmentId: Long,
        newDateTimeMillis: Long
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(appointment: Appointment): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(appointments: List<Appointment>)

    @Update
    suspend fun update(appointment: Appointment)

    @Delete
    suspend fun delete(appointment: Appointment)

    @Query("DELETE FROM appointments WHERE id = :appointmentId")
    suspend fun deleteById(appointmentId: Long)

    @Query("SELECT COUNT(*) FROM appointments")
    suspend fun count(): Int
}
