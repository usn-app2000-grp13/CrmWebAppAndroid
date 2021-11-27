package no.usn.gruppe4.crmwebappandroid.models.stats

import no.usn.gruppe4.crmwebappandroid.models.employee.Employee

class employeePop(
    val _id: String,
    val count: Int,
    val employee: List<Employee>
) {
    override fun toString(): String {
        return "id: $_id count: $count Employee: $employee"
    }
    data class employeePopRes(
        val `data`: List<employeePop>,
        val success: Boolean
    )
}