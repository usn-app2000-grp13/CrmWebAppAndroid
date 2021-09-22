package no.usn.gruppe4.crmwebappandroid.uicomponents

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.ActivityLoginBinding
import no.usn.gruppe4.crmwebappandroid.databinding.ActivityMainBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentLoginBinding
import no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.LoginFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding = ActivityLoginBinding.inflate(layoutInflater)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        val navController = this.findNavController(R.id.myNavHostFragment)
        //only for testing purposes!!
        //val sp = this?.getPreferences(Context.MODE_PRIVATE)

        /* enable this for login bypass after first time login

        if (sp.getBoolean("logged", false)){
            goToMainActivity()
            finish()
        }



        binding.btnLogin.setOnClickListener {
            goToMainActivity()
            sp.edit().putBoolean("logged", true).apply()
            finish()
        }
        */

    }
    fun goToMainActivity(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}