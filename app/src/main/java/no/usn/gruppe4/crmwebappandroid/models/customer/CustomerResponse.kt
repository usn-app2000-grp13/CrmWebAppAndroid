package no.usn.gruppe4.crmwebappandroid.models.customer

data class CustomerResponse(
    val `data`: List<Customer>,
    val success: Boolean
) {
}