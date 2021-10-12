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
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginActivity
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginViewModel
import no.usn.gruppe4.crmwebappandroid.uicomponents.MainActivity
import java.lang.Exception

private const val TAG = "LoginFragment"

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //inflate the layout and bind the binding
        binding = FragmentLoginBinding.inflate(inflater)

        //set the viewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.loginbtn.setOnClickListener {
                viewModel.loginCall(LoginRequest(binding.editTextTextPassword2.text.toString(), binding.editTextTextEmailAddress2.text.toString()))
            //the fragment KILLER!!!!
            //activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }

        viewModel.logged.observe(viewLifecycleOwner, {
            if (it == true){
                val i = Intent(activity, MainActivity::class.java)
                startActivity(i)
            }
        })
        binding.newCompanybtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_newCompanyFragment)
        }

       // binding.resetPasswordbtn.setOnClickListener {

           // findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
       // }

        //inflate the layout
        return binding.root
    }


}