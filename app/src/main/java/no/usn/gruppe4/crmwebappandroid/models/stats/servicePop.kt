package no.usn.gruppe4.crmwebappandroid.models.stats

import no.usn.gruppe4.crmwebappandroid.models.service.Service

data class servicePop(
    val _id: String,
    val count: Int,
    val service: List<Service>
) {
    data class servicePopRes(
        val `data`: List<servicePop>,
        val success: Boolean
    )
}