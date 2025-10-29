package com.example.mediturn.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mediturn.data.model.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {

    @Query("SELECT * FROM notifications ORDER BY createdAt DESC")
    fun observeNotifications(): Flow<List<Notification>>

    @Query(
        "SELECT * FROM notifications " +
            "WHERE patientId = :patientId " +
            "ORDER BY createdAt DESC"
    )
    fun observeByPatient(patientId: Long): Flow<List<Notification>>

    @Query(
        "SELECT * FROM notifications " +
            "WHERE doctorId = :doctorId " +
            "ORDER BY createdAt DESC"
    )
    fun observeByDoctor(doctorId: Long): Flow<List<Notification>>

    @Query("SELECT * FROM notifications WHERE id = :notificationId LIMIT 1")
    suspend fun getById(notificationId: Long): Notification?

    @Query("UPDATE notifications SET isRead = 1 WHERE id = :notificationId")
    suspend fun markAsRead(notificationId: Long)

    @Query(
        "UPDATE notifications SET isRead = 1 WHERE " +
            "(patientId = :patientId OR :patientId IS NULL) AND " +
            "(doctorId = :doctorId OR :doctorId IS NULL)"
    )
    suspend fun markAllAsRead(patientId: Long?, doctorId: Long?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(notification: Notification): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(notifications: List<Notification>)

    @Update
    suspend fun update(notification: Notification)

    @Delete
    suspend fun delete(notification: Notification)

    @Query("DELETE FROM notifications WHERE id = :notificationId")
    suspend fun deleteById(notificationId: Long)

    @Query("SELECT COUNT(*) FROM notifications")
    suspend fun count(): Int

    @Query("SELECT COUNT(*) FROM notifications WHERE isRead = 0")
    fun observeUnreadCount(): Flow<Int>
}
