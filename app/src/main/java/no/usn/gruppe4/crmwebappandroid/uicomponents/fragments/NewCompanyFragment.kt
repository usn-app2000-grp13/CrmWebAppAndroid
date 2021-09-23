package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentLoginBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewCompanyBinding


class NewCompanyFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentNewCompanyBinding.inflate(inflater)
        //inflate the layout and bind the binding
        // Inflate the layout for this fragment
        return binding.root
    }

}