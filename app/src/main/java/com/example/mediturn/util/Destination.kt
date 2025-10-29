package com.example.mediturn.util

object Destination {
    const val HOME = "home"
    const val SEARCH = "search"
    const val APPOINTMENTS = "appointments"
    const val PROFILE = "profile"
    const val SPECIALTIES = "specialties"
    const val EDIT_PROFILE = "edit_profile"
    const val SETTINGS = "settings"
    const val DOCTOR_DETAIL = "doctor_detail/{doctorId}"
    const val SCHEDULE_APPOINTMENT = "schedule_appointment/{doctorId}?appointmentId={appointmentId}"
    const val NOTIFICATIONS = "notifications"
    
    fun doctorDetail(doctorId: String) = "doctor_detail/$doctorId"
    fun scheduleAppointment(doctorId: String, appointmentId: Long? = null) = 
        if (appointmentId != null) "schedule_appointment/$doctorId?appointmentId=$appointmentId"
        else "schedule_appointment/$doctorId"
}
