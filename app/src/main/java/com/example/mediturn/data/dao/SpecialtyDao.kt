package com.example.mediturn.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mediturn.data.model.EspecialityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecialtyDao {

    @Query("SELECT * FROM specialties ORDER BY name ASC")
    fun observeSpecialties(): Flow<List<EspecialityEntity>>

    @Query("SELECT * FROM specialties WHERE id = :specialtyId LIMIT 1")
    suspend fun getById(specialtyId: Long): EspecialityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(specialty: EspecialityEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(specialties: List<EspecialityEntity>)

    @Update
    suspend fun update(specialty: EspecialityEntity)

    @Delete
    suspend fun delete(specialty: EspecialityEntity)

    @Query("DELETE FROM specialties WHERE id = :specialtyId")
    suspend fun deleteById(specialtyId: Long)

    @Query("SELECT COUNT(*) FROM specialties")
    suspend fun count(): Int
}
