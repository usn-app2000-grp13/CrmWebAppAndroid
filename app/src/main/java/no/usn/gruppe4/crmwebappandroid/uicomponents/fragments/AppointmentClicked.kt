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
import java.util.*
import kotlin.collections.ArrayList


class AppointmentClicked : Fragment() , DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var appointment: Appointment? = null
    lateinit var textDate: TextView
    lateinit var textTime: TextView
    lateinit var textDuration: TextView
    lateinit var textComment: TextView
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
        val binding = FragmentAppointmentClickedBinding.inflate(inflater)

        textDate = binding.editAppDate
        textTime = binding.editAppTime
        textDuration = binding.editAppTxtDuration
        textComment = binding.editAppTxtComment
        //spinner services
        ArrayAdapter.createFromResource(requireContext(), R.array.services, android.R.layout.simple_spinner_dropdown_item).also {
                adapter ->  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.editAppService.adapter = adapter
        }
        //list services
        var services: ArrayList<String> = ArrayList()
        services.add(appointment?.service.toString())
        val serviceAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, services)
        binding.editAppServiceList.adapter = serviceAdapter
            binding.btnEditAppService.setOnClickListener {
                val selectedService = binding.editAppService.selectedItem
                services.add(selectedService as String)
                serviceAdapter.notifyDataSetChanged()
        }


        //spinner customers
        ArrayAdapter.createFromResource(requireContext(), R.array.customers, android.R.layout.simple_spinner_dropdown_item).also {
                adapter ->  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.editAppCustomer.adapter = adapter
        }

        //list customers
        var customers: ArrayList<String> = ArrayList()
        customers.add(appointment?.customer.toString())
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
        employees.add(appointment?.employee.toString())
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

        binding.btnEditAppEdit.setOnClickListener {
            flipEditable(binding.editAppTxtComment)
            flipEditable(binding.editAppTxtDuration)
            if (!editMode){
                binding.btnEditAppDate.visibility = View.VISIBLE
                binding.btnEditAppTime.visibility = View.VISIBLE
                binding.rlCustomers.visibility = View.VISIBLE
                binding.rlEmployees.visibility = View.VISIBLE
                binding.rlServices.visibility = View.VISIBLE
                binding.btnEditAppEdit.text = "Done"
                editMode = !editMode
            }else{
                binding.btnEditAppDate.visibility = View.GONE
                binding.btnEditAppTime.visibility = View.GONE
                binding.rlCustomers.visibility = View.GONE
                binding.rlEmployees.visibility = View.GONE
                binding.rlServices.visibility = View.GONE
                binding.btnEditAppEdit.text = "Edit"
                editMode = !editMode
            }
        }
        textDate.text = appointment?.date
        textTime.text = appointment?.time
        textDuration.text = appointment?.duration + " Minutes"
        textComment.text = appointment?.comment

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
    fun flipEditable(element: EditText){
        if (!editMode){
            element.isClickable = true
            element.isCursorVisible = true
            element.isFocusable = true
            element.isFocusableInTouchMode = true
        }else{
            element.isClickable = false
            element.isCursorVisible = false
            element.isFocusable = false
            element.isFocusableInTouchMode = false
        }

    }

}