package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEditServiceBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentServiceBinding

class EditServiceFragment : Fragment() {

    lateinit var binding : FragmentEditServiceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditServiceBinding.inflate(inflater)
        return binding.root
    }

}