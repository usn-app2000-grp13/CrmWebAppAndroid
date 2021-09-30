package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentLoginBinding
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginActivity
import no.usn.gruppe4.crmwebappandroid.uicomponents.MainActivity


class LoginFragment : Fragment() {




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //inflate the layout and bind the binding
        val binding = FragmentLoginBinding.inflate(inflater)
        // Inflate the layout for this fragment

        binding.loginbtn.setOnClickListener {
            val i = Intent(activity, MainActivity::class.java)
            startActivity(i)
            //the fragment KILLER!!!!
            //activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
        }
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