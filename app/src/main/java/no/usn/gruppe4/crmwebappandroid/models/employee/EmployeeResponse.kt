package no.usn.gruppe4.crmwebappandroid.models.employee



data class EmployeeResponse(
    val `data`: List<Employee>,
    val success: Boolean
) {
}