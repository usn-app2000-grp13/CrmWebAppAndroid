package no.usn.gruppe4.crmwebappandroid.models.appointment

import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment

class Datasource {
    fun loadAppointments(): List<Appointment>{
        return listOf<Appointment>(
            Appointment(R.string.calTime1, R.string.calCust1, R.string.calServ1),
            Appointment(R.string.calTime2, R.string.calCust2, R.string.calServ2),
            Appointment(R.string.calTime3, R.string.calCust3, R.string.calServ3),
            Appointment(R.string.calTime4, R.string.calCust4, R.string.calServ4),
            Appointment(R.string.calTime5, R.string.calCust5, R.string.calServ5),
            Appointment(R.string.calTime6, R.string.calCust6, R.string.calServ6),
            Appointment(R.string.calTime7, R.string.calCust7, R.string.calServ7),
            Appointment(R.string.calTime8, R.string.calCust8, R.string.calServ8),
            Appointment(R.string.calTime9, R.string.calCust9, R.string.calServ9)
        )
    }
}