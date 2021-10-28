package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentLoginBinding
import no.usn.gruppe4.crmwebappandroid.models.login.LoginRequest
import no.usn.gruppe4.crmwebappandroid.models.login.ResetPasswordRequest
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginActivity
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginViewModel
import no.usn.gruppe4.crmwebappandroid.uicomponents.MainActivity
import java.lang.Exception

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //inflate the layout and bind the binding
        binding = FragmentLoginBinding.inflate(inflater)

        //set the viewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        //onClickListener for login button sends a loginRequest to the viewModel using the email and password
        binding.loginbtn.setOnClickListener {
            viewModel.loginCall(LoginRequest(binding.editTextTextPassword2.text.toString(), binding.editTextTextEmailAddress2.text.toString()))
            //the fragment KILLER!!!!
            //activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        //status indicator observed for scene modification 1 = show login screen 2 = show progress bar 3 = login success
        viewModel.status.observe(viewLifecycleOwner, {
            if (it == 1){
                binding.constraintLayout.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }else if (it == 2){
                binding.constraintLayout.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }else if (it == 3){
                val i = Intent(activity, MainActivity::class.java)
                startActivity(i)
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
            }else {
                //make email field show error
            }
        }

        //inflate the layout
        return binding.root
    }


}