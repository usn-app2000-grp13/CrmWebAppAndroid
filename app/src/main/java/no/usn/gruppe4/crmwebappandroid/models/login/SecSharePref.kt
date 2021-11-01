package no.usn.gruppe4.crmwebappandroid.models.login

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SecSharePref(
    context: Context, name: String
): SharedPrefInterface {
    private lateinit var sharedPreferences: SharedPreferences

    init {
        try {
            //makes the master key
            val masterKeyAlias = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
            //creates a encrypted shared preference file
            sharedPreferences = EncryptedSharedPreferences.create(
                context,
                name,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }catch (e: Exception){

        }

    }


    override fun get(name: String): String {
        return sharedPreferences.getString(name, null)!!
    }

    override fun getBoolean(name: String): Boolean {
        return sharedPreferences.getBoolean(name, false)
    }

    override fun put(name: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(name, value).apply()
    }

    override fun putBoolean(name: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(name, value).apply()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}