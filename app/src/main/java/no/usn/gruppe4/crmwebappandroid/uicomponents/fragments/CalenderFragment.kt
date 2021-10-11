package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ConcatAdapter
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentCalenderBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.*
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginViewModel
import no.usn.gruppe4.crmwebappandroid.uicomponents.MainActivity



class CalenderFragment : Fragment() {

    private lateinit var viewModel: CalanderViewModel
    private lateinit var binding: FragmentCalenderBinding
    private val appointmentList = mutableListOf<AppointmentResponse.Appointment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentCalenderBinding.inflate(inflater)
        //set the viewModel
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)
        viewModel.getAppointments()
        //val myDataset = getAppointmentList()
        val adapter = AppointmentAdapter(requireContext(), appointmentList)
        val headerAdapter = AppointmentHeaderAdapter()
        val concatAdapter = ConcatAdapter(headerAdapter, adapter)

        binding.recyclerView.adapter = concatAdapter

        viewModel.appointment.observe(viewLifecycleOwner, { appointments ->
            appointmentList.clear()
            appointmentList.addAll(appointments)
            adapter.notifyDataSetChanged()
        })


        adapter.setOnItemClickListener(object: AppointmentAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                /*
                val bundle = Bundle()
                bundle.putString("appointment", appointmentList[position]._id)
                findNavController().navigate(R.id.action_calenderFragment_to_appointmentClicked, bundle)

                 */
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
            //Log.i("JSONENTRY", jsonFileString)
        }

        val gson = Gson()
        val listAppointmentType = object : TypeToken<List<Appointment>>() {}.type

        var appointments: List<Appointment> = gson.fromJson(jsonFileString, listAppointmentType)
        //appointments.forEachIndexed { idx, appointment -> Log.i("data", ">Item $idx: \n$appointment") }
        return appointments
    }


}