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
    private val serviceList = mutableListOf<Service>()

    private val customerList = mutableListOf<Customer>()

    // TODO: 14.10.2021 change later to real employee list
    private val employeeList = mutableListOf<Employee>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //binding
        val binding = FragmentNewAppointmentBinding.inflate(inflater)

        //viewModel instance
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)
        viewModel.getSchemaData()
        textDate = binding.editAppDate
        textTime = binding.editAppTime

        var selectedService: List<Service>
        var selectedCustomer: List<Customer>
        var selectedEmployee: List<Employee>

        //list services
        var services: ArrayList<Service> = ArrayList()
        val serviceAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, services)
        binding.editAppServiceList.adapter = serviceAdapter
        binding.btnEditAppService.setOnClickListener {
            val ser = binding.editAppService.selectedItem

            serviceAdapter.notifyDataSetChanged()
        }


        //spinner customers
        ArrayAdapter.createFromResource(requireContext(), R.array.customers, android.R.layout.simple_spinner_dropdown_item).also {
                adapter ->  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.editAppCustomer.adapter = adapter
        }

        //list customers
        var customers: ArrayList<String> = ArrayList()
        val customerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, customers)
        binding.editAppCustomerList.adapter = customerAdapter
        binding.btnEditAppCustomer.setOnClickListener {
            val selectedCustomer = binding.editAppCustomer.selectedItem
            customers.add(selectedCustomer as String)
            customerAdapter.notifyDataSetChanged()
        }


        //spinner employees
        ArrayAdapter.createFromResource(requireContext(), R.array.employees, android.R.layout.simple_spinner_dropdown_item).also {
                adapter ->  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.editAppEmployee.adapter = adapter
        }

        //list employees
        var employees: ArrayList<String> = ArrayList()
        val employeeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, employees)
        binding.editAppEmployeeList.adapter = employeeAdapter
        binding.btnEditAppEmployee.setOnClickListener {
            val selectedEmployee = binding.editAppEmployee.selectedItem
            employees.add(selectedEmployee as String)
            employeeAdapter.notifyDataSetChanged()
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
            val date = binding.editAppDate.text.toString()
            val time = binding.editAppTime.text.toString()
            val comment = binding.editAppTxtComment.text.toString()
            val duration = binding.editAppTxtDuration.text.toString()
            //var newAppointment: Appointment = Appointment(date, time, comment, duration, customers[0], services[0], employees[0])
            //Log.i("NewCustomer", newAppointment.toString())
            findNavController().navigate(R.id.action_newAppointmentFragment_to_calenderFragment)
        }

        viewModel.services.observe(viewLifecycleOwner, {
            serviceList.clear()
            serviceList.addAll(it)

            //spinner services
            binding.editAppService.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,getServiceArray())
        })
        viewModel.customers.observe(viewLifecycleOwner, {
            customerList.clear()
            customerList.addAll(it)
        })

        // TODO: 14.10.2021 change later to real employee object
        viewModel.employees.observe(viewLifecycleOwner, {
            employeeList.clear()
            employeeList.addAll(it)
        })
        val aa = getServiceArray()

        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayofmonth: Int) {
        textDate.text = reformatDateTime(dayofmonth) + " / " + reformatDateTime(month) + " / " + reformatDateTime(year)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        textTime.text = reformatDateTime(hour) + ":" + reformatDateTime(minute)
    }
    fun reformatDateTime(element: Int): String{
        var res = ""
        if (element < 10){
            res += "0$element"
        }else res += element.toString()
        return res
    }

    fun getServiceArray(): Array<String>{

        val asc = Array(serviceList.size) {
            i -> serviceList.get(i).name.toString()
        }
        asc.forEach { Log.i("Array", it) }
        return asc
    }


}