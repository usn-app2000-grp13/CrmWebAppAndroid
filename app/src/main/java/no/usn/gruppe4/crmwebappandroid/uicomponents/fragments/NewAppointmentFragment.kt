package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_new_appointment.*
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewAppointmentBinding
import java.util.*

class NewAppointmentFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0
    lateinit var btnDate: Button
    lateinit var btnTime: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewAppointmentBinding.inflate(inflater)
        ArrayAdapter.createFromResource(requireContext(), R.array.services, android.R.layout.simple_spinner_dropdown_item).also {
            adapter ->  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }

        btnDate = binding.naDate
        btnTime = binding.naTime

        btnDate.setOnClickListener {
            getDateTimeCalender()
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        btnTime.setOnClickListener {
            getDateTimeCalender()
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }

        binding.naSubmit.setOnClickListener {
            val fname = binding.txtFirstName.text
            val lname = binding.txtLastName.text
            val phone = binding.txtPhone.text
            val email = binding.txtEmail.text
            val service = binding.spinner.selectedItem.toString()

            println("$savedDay / $savedMonth / $savedYear \n " +
                    "\"$savedHour : $savedMinute\" \n " +
                    "$fname $lname $phone $email $service" )
        }

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun getDateTimeCalender(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)

    }


    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year
        btnDate.text = "$savedDay / $savedMonth / $savedYear"
    }

    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        savedHour = p1
        savedMinute = p2
        btnTime.text = "$savedHour : $savedMinute"
    }


}