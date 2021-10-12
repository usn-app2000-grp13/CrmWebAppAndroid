package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEditServiceBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentServiceBinding
import no.usn.gruppe4.crmwebappandroid.models.service.Service

class EditServiceFragment : Fragment() {

    lateinit var binding : FragmentEditServiceBinding
    lateinit var service : Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getParcelable<Service>("service").let { el->
            service = el!!
        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditServiceBinding.inflate(inflater)
        binding.editAppTxtTitle.setText(service.name)
        binding.editAppTxtDescription.setText(service.description)
        binding.editAppTxtDuration.setText(service.duration)
        binding.editAppTxtPrice.setText(service.price)
        return binding.root
    }

}