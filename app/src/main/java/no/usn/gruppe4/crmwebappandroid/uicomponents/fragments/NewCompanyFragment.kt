package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentLoginBinding


class NewCompanyFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //inflate the layout and bind the binding
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_company, container, false)
    }

}