package com.example.mediturn.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mediturn.data.model.DoctorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {

    @Query("SELECT * FROM doctors ORDER BY name ASC, lastname ASC")
    fun observeDoctors(): Flow<List<DoctorEntity>>

    @Query("SELECT * FROM doctors WHERE id = :doctorId LIMIT 1")
    suspend fun getById(doctorId: Long): DoctorEntity?

    @Query("SELECT * FROM doctors WHERE id = :doctorId LIMIT 1")
    fun observeById(doctorId: Long): Flow<DoctorEntity?>

    @Query("SELECT * FROM doctors WHERE specialtyId = :specialtyId ORDER BY name ASC, lastname ASC")
    fun observeBySpecialty(specialtyId: Long): Flow<List<DoctorEntity>>

    @Query("SELECT * FROM doctors WHERE hasTeleconsultation = 1 ORDER BY rating DESC")
    fun observeTeleconsultations(): Flow<List<DoctorEntity>>

    @Query("SELECT * FROM doctors ORDER BY rating DESC LIMIT :limit")
    fun observeTopRated(limit: Int): Flow<List<DoctorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(doctor: DoctorEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(doctors: List<DoctorEntity>)

    @Update
    suspend fun update(doctor: DoctorEntity)

    @Delete
    suspend fun delete(doctor: DoctorEntity)

    @Query("DELETE FROM doctors WHERE id = :doctorId")
    suspend fun deleteById(doctorId: Long)

    @Query("SELECT COUNT(*) FROM doctors")
    suspend fun count(): Int
}
