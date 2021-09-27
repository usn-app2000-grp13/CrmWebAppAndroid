package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentCalenderBinding
import no.usn.gruppe4.crmwebappandroid.models.appointment.Datasource
import no.usn.gruppe4.crmwebappandroid.models.appointment.AppointmentAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment


class CalenderFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val binding = FragmentCalenderBinding.inflate(inflater)
        val myDataset = getAppointmentList()
        val adapter = AppointmentAdapter(requireContext(), myDataset)
        binding.recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object: AppointmentAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Log.i("CLICK", "onItemClick: ${position}")
                val bundle = Bundle()
                bundle.putInt("appointmentId", position)
                bundle.putString("appointmentTime", myDataset[position].time.toString())
                bundle.putString("appointmentCus", myDataset[position].customer.toString())
                bundle.putString("appointmentSer", myDataset[position].service.toString())
                findNavController().navigate(R.id.action_calenderFragment_to_appointmentClicked, bundle)
            }
        })

        binding.recyclerView.setHasFixedSize(true)

        binding.fabNewAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_calenderFragment_to_newAppointmentFragment)
        }
        return binding.root
    }

    fun getAppointmentList(): List<Appointment>{
        val jsonFileString = Datasource().getJSONDataFromAsset(requireContext(), "appointments.json")
        if (jsonFileString != null) {
            Log.i("JSONENTRY", jsonFileString)
        }

        val gson = Gson()
        val listAppointmentType = object : TypeToken<List<Appointment>>() {}.type

        var appointments: List<Appointment> = gson.fromJson(jsonFileString, listAppointmentType)
        appointments.forEachIndexed { idx, appointment -> Log.i("data", ">Item $idx: \n$appointment") }
        return appointments
    }

}