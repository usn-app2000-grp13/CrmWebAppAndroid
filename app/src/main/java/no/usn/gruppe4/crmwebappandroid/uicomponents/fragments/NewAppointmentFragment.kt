package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_new_appointment.*
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewAppointmentBinding
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.appointment.AppointmentResponse
import no.usn.gruppe4.crmwebappandroid.models.appointment.Datasource
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import java.util.*

class NewAppointmentFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    lateinit var textDate: TextView
    lateinit var textTime: TextView
    private lateinit var viewModel: CalanderViewModel
    private lateinit var binding: FragmentNewAppointmentBinding
    private var serviceList = mutableListOf<Service>()
    private var customerList = mutableListOf<Customer>()
    private var employeeList = mutableListOf<Employee>()

    private var selectedServices = mutableListOf<Service>()
    private var selectedCustomers = mutableListOf<Customer>()
    private var selectedEmployees = mutableListOf<Employee>()
    private lateinit var appointment: Appointment.newAppointment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel instance
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)

        appointment = Appointment.newAppointment(null, null, "", null, mutableListOf(), mutableListOf(), mutableListOf())
        viewModel.setCurAppointment(appointment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //binding
        binding = FragmentNewAppointmentBinding.inflate(inflater)


        viewModel.curAppointment.observe(viewLifecycleOwner, {
            appointment = it
            updateLists()
        })

        viewModel.getSchemaData()

        textDate = binding.editAppDate
        textTime = binding.editAppTime

        //Services selection
        binding.btnEditAppService.setOnClickListener {
            val selectedService = binding.editAppService.selectedItem
            appointment.addService(selectedService as Service)
            selectedServices.add(selectedService)
            viewModel.setCurAppointment(appointment)
            Log.i("service change", appointment.toString())
        }

        //Customers selection
        binding.btnEditAppCustomer.setOnClickListener {
            val selectedCustomer = binding.editAppCustomer.selectedItem
            appointment.addCustomer(selectedCustomer as Customer)
            selectedCustomers.add(selectedCustomer)
            viewModel.setCurAppointment(appointment)
            Log.i("customer change", appointment.toString())
        }

        //Employees selection
        binding.btnEditAppEmployee.setOnClickListener {
            val selectedEmployee = binding.editAppEmployee.selectedItem
            appointment.addEmployee(selectedEmployee as Employee)
            selectedEmployees.add(selectedEmployee)
            viewModel.setCurAppointment(appointment)
            Log.i("customer change", appointment.toString())
        }


        //date selector
        binding.btnEditAppDate.setOnClickListener {
            val cal = Datasource().getTodayCalender()
            val year: Int? = cal?.get(Calendar.YEAR)
            val month: Int? = cal?.get(Calendar.MONTH)
            val day: Int? = cal?.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(), this, year!!, month!!, day!!).show()
        }

        //time selector
        binding.btnEditAppTime.setOnClickListener {
            val cal = Datasource().getTodayCalender()
            val hour: Int? = cal?.get(Calendar.HOUR)
            val minute: Int? = cal?.get(Calendar.MINUTE)
            TimePickerDialog(requireContext(), this, hour!!, minute!!, true).show()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_newAppointmentFragment_to_calenderFragment)
        }

        binding.btnSubmit.setOnClickListener {
            var duration = 0
            for (i in selectedServices){
                duration += i.duration!!
            }
            appointment.comment = binding.editAppTxtComment.text.toString()
            appointment.duration = duration
            if (appointment.date != null && appointment.timeindex != null){
                Log.i("NewCustomer", appointment.toString())
                viewModel.newAppointment(appointment)
            }

            findNavController().navigate(R.id.action_newAppointmentFragment_to_calenderFragment)
        }

        viewModel.services.observe(viewLifecycleOwner, {
            serviceList.clear()
            serviceList.addAll(it)

            //spinner services
            binding.editAppService.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, serviceList)
        })
        viewModel.customers.observe(viewLifecycleOwner, {
            customerList.clear()
            customerList.addAll(it)

            //spinner customers
            binding.editAppCustomer.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, customerList)
        })

        viewModel.employees.observe(viewLifecycleOwner, {
            employeeList.clear()
            employeeList.addAll(it)

            //spinner employees
            binding.editAppEmployee.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, employeeList)
        })

        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayofmonth: Int) {
        textDate.text = reformatDateTime(dayofmonth) + " / " + reformatDateTime(month) + " / " + reformatDateTime(year)
        val cal = Calendar.getInstance()
        cal.clear()
        cal.set(year,month,dayofmonth)
        val date = Date(cal.timeInMillis)
        appointment.date = date
        Log.i("date change", appointment.toString())
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        textTime.text = reformatDateTime(hour) + ":" + reformatDateTime(minute)
        val timeindex = (hour*60) + minute
        appointment.timeindex = timeindex
        Log.i("Time change", appointment.toString())
    }

    fun reformatDateTime(element: Int): String{
        var res = ""
        if (element < 10){
            res += "0$element"
        }else res += element.toString()
        return res
    }

    fun updateLists(){
        binding.LLservices.removeAllViews()
        for (i in selectedServices){
            var serviceElement = TextView(requireContext())
            serviceElement.text = i.toString()
            serviceElement.textSize = 15.0f
            serviceElement.setOnClickListener {
                appointment.removeService(i)
                selectedServices.remove(i)
                viewModel.setCurAppointment(appointment)
            }
            binding.LLservices.addView(serviceElement)
        }

        binding.LLCustomers.removeAllViews()
        for (i in selectedCustomers){
            var customerElement = TextView(requireContext())
            customerElement.text = i.toString()
            customerElement.textSize = 15.0f
            customerElement.setOnClickListener {
                appointment.removeCustomer(i)
                selectedCustomers.remove(i)
                viewModel.setCurAppointment(appointment)
            }
            binding.LLCustomers.addView(customerElement)
        }

        binding.LLEmployees.removeAllViews()
        for (i in selectedEmployees){
            var employeeElement = TextView(requireContext())
            employeeElement.text = i.toString()
            employeeElement.textSize = 15.0f
            employeeElement.setOnClickListener {
                appointment.removeEmployee(i)
                selectedEmployees.remove(i)
                viewModel.setCurAppointment(appointment)
            }
            binding.LLEmployees.addView(employeeElement)
        }

    }


}