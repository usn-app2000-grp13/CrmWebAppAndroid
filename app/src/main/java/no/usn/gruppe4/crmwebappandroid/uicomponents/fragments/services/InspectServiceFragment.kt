package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentInspectServiceBinding
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.service.Service

class InspectServiceFragment : Fragment() {

    lateinit var binding : FragmentInspectServiceBinding

    lateinit var service : Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Service>("service").let { el->
            service = el!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInspectServiceBinding.inflate(inflater)
        binding.tvDescription.text = service.description

        return binding.root
    }

}