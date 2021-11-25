package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentLoginBinding
import no.usn.gruppe4.crmwebappandroid.models.login.*
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginViewModel
import no.usn.gruppe4.crmwebappandroid.uicomponents.MainActivity

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedPreferences: SharedPrefInterface

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //inflate the layout and bind the binding
        binding = FragmentLoginBinding.inflate(inflater)

        //make link the secure shared preference file
        sharedPreferences = SecSharePref(requireContext(), "secrets")


        //set the viewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        //logged check
        if (sharedPreferences.getBoolean("logged")){
            binding.constraintLayout.visibility = View.GONE
            viewModel.loginCall(LoginRequest(sharedPreferences.get("password"), sharedPreferences.get("username")))
        }

        //onClickListener for login button sends a loginRequest to the viewModel using the email and password
        binding.loginbtn.setOnClickListener {
            if (binding.editTextTextPassword2.text.length > 1 && binding.editTextTextEmailAddress2.text.length > 1){
                viewModel.loginCall(LoginRequest(binding.editTextTextPassword2.text.toString(), binding.editTextTextEmailAddress2.text.toString()))
                sharedPreferences.put("username", binding.editTextTextEmailAddress2.text.toString())
                sharedPreferences.put("password", binding.editTextTextPassword2.text.toString())
            }
            //the fragment KILLER!!!!
            //activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
        viewModel.session.observe(viewLifecycleOwner, {

            sharedPreferences.put("name", "${viewModel.session.value!!.firstname} ${viewModel.session.value!!.lastname}")
            sharedPreferences.put("id", viewModel.session.value!!.id)
            sharedPreferences.putBoolean("logged", true)
            sharedPreferences.put("level", viewModel.session.value!!.level.toString())
            val i = Intent(activity, MainActivity::class.java)
            startActivity(i)
        })

        //Api error handling
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            if (it == null){
                Log.i("ErrorMessageTest", "No errors")
                binding.constraintLayout.visibility = View.GONE
            } else {
                binding.constraintLayout.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.status.observe(viewLifecycleOwner, {
            if (it == 1){
                binding.constraintLayout.visibility = View.VISIBLE
            }
            if (it == 2){
                binding.constraintLayout.visibility = View.GONE
            }
            if (it == 3){
                //can add login wait screen here
            }
        })

        //Api call loading
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it == false){
                binding.progressBar.visibility = View.GONE
            }else {
                binding.constraintLayout.visibility = View.GONE
            }
        })

        //onClickListener for new Company sends user to new company form
        binding.newCompanybtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_newCompanyFragment)
        }


        //onClickListener for the resetPassword button will use the email of a user to send a password reset request to the api (this sends a email)
        binding.resetPasswordbtn.setOnClickListener {
            val email = binding.editTextTextEmailAddress2.text.toString()
            if (email != null){
                viewModel.resetPassword(ResetPasswordRequest(email))
                findNavController().navigate(R.id.action_loginFragment_to_emailResetFragment)
            }else {
                //make email field show error
            }
        }

        //inflate the layout
        return binding.root
    }
}