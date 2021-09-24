package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewServiceBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentServiceBinding

class NewServiceFragment : Fragment() {

    lateinit var binding : FragmentNewServiceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewServiceBinding.inflate(inflater)
        return binding.root
    }

}