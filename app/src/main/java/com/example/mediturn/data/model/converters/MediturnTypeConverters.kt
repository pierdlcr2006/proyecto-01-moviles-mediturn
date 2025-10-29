package com.example.mediturn.data.model.converters

import androidx.room.TypeConverter
import com.example.mediturn.data.model.AppointmentStatus
import com.example.mediturn.data.model.DayAvailability
import com.example.mediturn.data.model.TimeSlot
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.collections.emptyList

/**
 * Converts complex model types so they can be stored in the Room database.
 */
class MediturnTypeConverters {

    private val gson = Gson()

    private val dayAvailabilityListType = object : TypeToken<List<DayAvailability>>() {}.type
    private val timeSlotListType = object : TypeToken<List<TimeSlot>>() {}.type

    @TypeConverter
    fun fromDayAvailabilityList(value: List<DayAvailability>?): String =
        gson.toJson(value ?: listOf(String()), dayAvailabilityListType)

    @TypeConverter
    fun toDayAvailabilityList(value: String?): List<DayAvailability> =
        if (value.isNullOrBlank()) {
            emptyList()
        } else {
            gson.fromJson(value, dayAvailabilityListType)
        }

    @TypeConverter
    fun fromTimeSlotList(value: List<TimeSlot>?): String =
        gson.toJson(value ?: listOf(String()), timeSlotListType)

    @TypeConverter
    fun toTimeSlotList(value: String?): List<TimeSlot> =
        if (value.isNullOrBlank()) {
            emptyList()
        } else {
            gson.fromJson(value, timeSlotListType)
        }

    @TypeConverter
    fun fromAppointmentStatus(status: AppointmentStatus?): String? = status?.name

    @TypeConverter
    fun toAppointmentStatus(value: String?): AppointmentStatus? =
        value?.let { AppointmentStatus.valueOf(it) }
}
