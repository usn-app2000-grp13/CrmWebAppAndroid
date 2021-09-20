package no.usn.gruppe4.crmwebappandroid.uicomponents

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import no.usn.gruppe4.crmwebappandroid.MainActivity
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        //only for testing purposes!!
        val sp = this?.getPreferences(Context.MODE_PRIVATE)

        if (sp.getBoolean("logged", false)){
            goToMainActivity()
            finish()
        }

        binding.btnLogin.setOnClickListener {
            goToMainActivity()
            sp.edit().putBoolean("logged", true).apply()
            finish()
        }

        setContentView(binding.root)
    }
    fun goToMainActivity(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}