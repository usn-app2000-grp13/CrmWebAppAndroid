package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentCalenderBinding
import no.usn.gruppe4.crmwebappandroid.models.appointment.Datasource
import no.usn.gruppe4.crmwebappandroid.models.appointment.ItemAdapter


class CalenderFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentCalenderBinding.inflate(inflater)

        val myDataset = Datasource().loadAppointments()

        binding.recyclerView.adapter = ItemAdapter(requireContext(), myDataset)
        binding.recyclerView.setHasFixedSize(true)

        binding.fabNewAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_calenderFragment_to_newAppointmentFragment)
        }
        return binding.root
    }

}