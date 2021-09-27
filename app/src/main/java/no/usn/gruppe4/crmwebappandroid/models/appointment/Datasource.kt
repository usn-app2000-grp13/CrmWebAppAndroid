package no.usn.gruppe4.crmwebappandroid.models.appointment

import android.content.Context
import no.usn.gruppe4.crmwebappandroid.R
import java.io.IOException
import java.nio.charset.Charset

class Datasource {
    //read in json from file
    fun getJSONDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use {
                it.readText()
            }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}