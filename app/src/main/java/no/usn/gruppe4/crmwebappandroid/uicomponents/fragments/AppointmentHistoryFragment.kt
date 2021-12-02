package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentAppointmentHistoryBinding
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.appointment.AppointmentHistoryAdapter
import no.usn.gruppe4.crmwebappandroid.models.login.SecSharePref
import no.usn.gruppe4.crmwebappandroid.models.login.SharedPrefInterface
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import java.util.*

class AppointmentHistoryFragment : Fragment() {

    private var appointmentList = mutableListOf<Appointment>()

    lateinit var binding: FragmentAppointmentHistoryBinding
    lateinit var viewModel: CalanderViewModel
    private lateinit var sharedPreferences: SharedPrefInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAppointmentHistoryBinding.inflate(inflater)
        sharedPreferences = SecSharePref(requireContext(), "secrets")
        val username = sharedPreferences.get("name")
        val id = sharedPreferences.get("id")
        //set the viewModel
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)

        viewModel.getMyAppointmentsDate(id, System.currentTimeMillis().minus(2629800000))



        val adapter = AppointmentHistoryAdapter(requireContext(), appointmentList)
        binding.historyList.adapter = adapter
        adapter.setOnItemClickListener(object : AppointmentHistoryAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.i("test", "test")
            }
        })

        viewModel.appointment.observe(viewLifecycleOwner, { appointments->
            appointmentList.clear()
            val appointmentIterator = appointments.iterator()
            while (appointmentIterator.hasNext()){
                val app = appointmentIterator.next()
                if (app.customers?.get(0)?._customer != null && app.services?.get(0)?._service != null && app.employees?.get(0)?._employee != null){
                    if (app.checkDatePast(Date())){
                        appointmentList.add(app)
                    }
                }
            }
            appointmentList.asReversed()
            adapter.notifyDataSetChanged()
            binding.historyList.scheduleLayoutAnimation()
        })

        // Inflate the layout for this fragment
        return binding.root
    }

}