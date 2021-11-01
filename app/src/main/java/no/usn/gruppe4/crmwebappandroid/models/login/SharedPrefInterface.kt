package no.usn.gruppe4.crmwebappandroid.models.login

interface SharedPrefInterface {
    fun get(name: String): String
    fun getBoolean(name: String): Boolean
    fun put(name: String, value: String)
    fun putBoolean(name: String, value: Boolean)
    fun clear()
}