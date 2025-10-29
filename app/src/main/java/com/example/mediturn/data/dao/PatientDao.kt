package com.example.mediturn.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mediturn.data.model.PatientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Query("SELECT * FROM patients ORDER BY fullName ASC")
    fun observePatients(): Flow<List<PatientEntity>>

    @Query("SELECT * FROM patients WHERE id = :patientId LIMIT 1")
    suspend fun getById(patientId: Long): PatientEntity?

    @Query("SELECT * FROM patients WHERE id = :patientId LIMIT 1")
    fun observeById(patientId: Long): Flow<PatientEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(patient: PatientEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(patients: List<PatientEntity>)

    @Update
    suspend fun update(patient: PatientEntity)

    @Delete
    suspend fun delete(patient: PatientEntity)

    @Query("DELETE FROM patients WHERE id = :patientId")
    suspend fun deleteById(patientId: Long)

    @Query("SELECT COUNT(*) FROM patients")
    suspend fun count(): Int
}
