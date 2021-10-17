package no.usn.gruppe4.crmwebappandroid.models.employee

import no.usn.gruppe4.crmwebappandroid.models.appointment.AppointmentResponse

data class EmployeeResponse(
    val `data`: List<AppointmentResponse._employee>,
    val success: Boolean
) {
}