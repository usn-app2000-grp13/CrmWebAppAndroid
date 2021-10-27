package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentAppointmentClickedBinding
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.appointment.Datasource
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AppointmentClicked : Fragment() {

    private var TAG = "AppointmentClicked"
    private var appointment: Appointment? = null
    lateinit var textDate: TextView
    lateinit var textTime: TextView
    lateinit var textDuration: TextView
    lateinit var textComment: TextView
    lateinit var binding: FragmentAppointmentClickedBinding
    var editMode = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Appointment>("appointment").let { el->
            appointment = el
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentClickedBinding.inflate(inflater)
        Log.i(TAG, "appointment Received: ${appointment.toString()}")

        binding.txtTime.setText(timeIndexFormat(appointment?.timeindex!!))
        binding.txtDate.setText(SimpleDateFormat("yyyy-MM-dd").format(appointment?.date))
        binding.txtDescription.setText(appointment?.comment.toString())

        populateLists()

        // Inflate the layout for this fragment
        return binding.root
    }


    fun populateLists(){
        binding.listCustomer.removeAllViews()
        binding.listService.removeAllViews()
        binding.ListEmployee.removeAllViews()
        for (i in appointment?.customers!!){
            var customerElement = TextView(requireContext())
            customerElement.text = "${i._customer?.firstname} ${i._customer?.lastname}"
            customerElement.textSize = 25.0f
            binding.listCustomer.addView(customerElement)
        }
        for (i in appointment?.employees!!){
            var employeesElement = TextView(requireContext())
            employeesElement.text = "${i._employee?.firstname} ${i._employee?.lastname}"
            employeesElement.textSize = 25.0f
            binding.ListEmployee.addView(employeesElement)
        }
        for (i in appointment?.services!!){
            var serviceElement = TextView(requireContext())
            serviceElement.text = "${i._service?.name}"
            serviceElement.textSize = 25.0f
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