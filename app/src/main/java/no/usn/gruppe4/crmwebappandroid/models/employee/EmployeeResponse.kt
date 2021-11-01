package no.usn.gruppe4.crmwebappandroid.models.employee



data class EmployeeResponse(
    val `data`: List<Employee>,
    val success: Boolean
) {
    data class SingleEmployeeResponse(
        val `data`: Employee,
        val success: Boolean
    )
}