package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentAppointmentClickedBinding


class AppointmentClicked : Fragment() {

    private var appointmentInt: Int = -1
    private var appointmentTime: String = ""
    private var appointmentCus: String = ""
    private var appointmentSer: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("appointmentId")?.let { apId ->
            this.appointmentInt = apId
        }
        arguments?.getString("appointmentTime")?.let { el ->
            this.appointmentTime = el
        }
        arguments?.getString("appointmentCus")?.let { el ->
            this.appointmentCus = el
        }
        arguments?.getString("appointmentSer")?.let { el ->
            this.appointmentSer = el
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAppointmentClickedBinding.inflate(inflater)
        binding.apseltext1.text = "lol" + appointmentInt.toString()
        binding.apseltext2.text = appointmentTime
        binding.apseltext3.text = appointmentCus
        binding.apseltext4.text = appointmentSer
        // Inflate the layout for this fragment
        return binding.root
    }

}