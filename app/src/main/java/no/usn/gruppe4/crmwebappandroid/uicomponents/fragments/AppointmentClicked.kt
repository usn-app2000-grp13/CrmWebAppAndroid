package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentAppointmentClickedBinding
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import java.text.SimpleDateFormat

class AppointmentClicked : Fragment() {

    private var TAG = "AppointmentClicked"
    private var appointment: Appointment? = null
    lateinit var binding: FragmentAppointmentClickedBinding
    private lateinit var viewModel: CalanderViewModel
    private var deleted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Appointment>("appointment").let { el->
            appointment = el
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAppointmentClickedBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)

        Log.i(TAG, "appointment Received: ${appointment.toString()}")

        binding.txtTime.setText(timeIndexFormat(appointment?.timeindex!!))
        binding.txtDate.setText(SimpleDateFormat("yyyy-MM-dd").format(appointment?.date))
        binding.txtDescription.setText(appointment?.comment.toString())

        binding.btnDeleteAppointment.setOnClickListener {
            viewModel.removeAppointment(IdRequest(appointment?._id))
            deleted = true
        }

        populateLists()

        viewModel.status.observe(viewLifecycleOwner, {
            if (it){
                if (deleted){
                    findNavController().navigate(R.id.action_appointmentClicked_to_calenderFragment)
                }
                binding.mainContainer.visibility = View.VISIBLE
                binding.progressBar2.visibility = View.GONE
            }else{
                binding.mainContainer.visibility = View.GONE
                binding.progressBar2.visibility = View.VISIBLE
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }


    fun populateLists(){
        binding.listCustomer.removeAllViews()
        binding.listService.removeAllViews()
        binding.ListEmployee.removeAllViews()
        for (i in appointment?.customers!!){
            val customerElement = TextView(requireContext())
            customerElement.text = "${i._customer?.firstname} ${i._customer?.lastname}"
            customerElement.textSize = 18.0f
            customerElement.setPadding(30,10,10,10)
            customerElement.setOnClickListener {
                Log.i(TAG, "you clicked: ${i.toString()}")
            }
            binding.listCustomer.addView(customerElement)
        }
        for (i in appointment?.employees!!){
            val employeesElement = TextView(requireContext())
            employeesElement.text = "${i._employee?.firstname} ${i._employee?.lastname}"
            employeesElement.textSize = 18.0f
            employeesElement.setPadding(30,10,10,10)
            employeesElement.setOnClickListener {
                Log.i(TAG, "you clicked: ${i.toString()}")
            }
            binding.ListEmployee.addView(employeesElement)
        }
        for (i in appointment?.services!!){
            val serviceElement = TextView(requireContext())
            serviceElement.text = "${i._service?.name}"
            serviceElement.textSize = 18.0f
            serviceElement.setPadding(30,10,10,10)
            serviceElement.setOnClickListener {
                Log.i(TAG, "you clicked: ${i.toString()}")
            }
            binding.listService.addView(serviceElement)
        }
    }

    fun timeIndexFormat(timeindex: Int): String{
        var res = ""
        val clockM = timeindex % 60
        val clockH = (timeindex - clockM) / 60

        if (clockH < 10){
            res += "0$clockH:"
        }else{
            res += "$clockH:"
        }
        if (clockM < 10) {
            res += "0$clockM"
        }else{
            res += "$clockM"
        }
        return res
    }

}