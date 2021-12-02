package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewAppointmentBinding
import no.usn.gruppe4.crmwebappandroid.models.Tools
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import java.util.*

class NewAppointmentFragment : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var textDate: TextView
    private lateinit var textTime: TextView
    private lateinit var SPService: Spinner
    private lateinit var SPCustomer: Spinner
    private lateinit var SPEmployee: Spinner
    private lateinit var viewModel: CalanderViewModel
    private lateinit var binding: FragmentNewAppointmentBinding
    private var serviceList = mutableListOf<Service>()
    private var customerList = mutableListOf<Customer>()
    private var employeeList = mutableListOf<Employee>()

    private var selectedServices = mutableListOf<Service>()
    private var availableServices = mutableListOf<Service>()
    private var selectedCustomers = mutableListOf<Customer>()
    private var availableCustomers = mutableListOf<Customer>()
    private var selectedEmployees = mutableListOf<Employee>()
    private var availableEmployees = mutableListOf<Employee>()
    private lateinit var appointment: Appointment.newAppointment
    private val tools =  Tools()

    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewModel instance
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)
        appointment = Appointment.newAppointment(null,null, null, "", null, mutableListOf(), mutableListOf(), mutableListOf())
        if (arguments != null){
            arguments?.getParcelable<Appointment>("appointment").let { el->
                appointment._id = el?._id
                appointment.date = el?.date
                appointment.comment = el?.comment.toString()
                appointment.duration = el?.duration
                appointment.timeindex = el?.timeindex
                el?.services?.forEach {
                    selectedServices.add(it._service!!)
                    appointment.addService(it._service as Service)
                }
                el?.employees?.forEach {
                    selectedEmployees.add(it._employee!!)
                    appointment.addEmployee(it._employee as Employee)
                }
                el?.customers?.forEach {
                    selectedCustomers.add(it._customer!!)
                    appointment.addCustomer(it._customer as Customer)

                }
                isEdit = true
            }

        }
        viewModel.setCurAppointment(appointment)
        appointment.toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        //binding
        binding = FragmentNewAppointmentBinding.inflate(inflater)

        viewModel.getSchemaData()

        textDate = binding.editAppDate
        textTime = binding.editAppTime
        SPService = binding.editAppService
        SPCustomer = binding.editAppCustomer
        SPEmployee = binding.editAppEmployee

        if (appointment.date == null){
            val today = Calendar.getInstance().time as Date
            appointment.date = today
            textDate.text = tools.formatDate(today)
            appointment.timeindex = ((today.hours+1)*60) + today.minutes
            textTime.text = tools.timeIndexFormat(appointment.timeindex!!)
        }

        //viewmodel observes curappointment
        viewModel.curAppointment.observe(viewLifecycleOwner, {
            it.also { appointment = it }
            //makes the chips required
            getChips()
        })

        //Services selection
        binding.btnEditAppService.setOnClickListener {
            val selectedService = binding.editAppService.selectedItem as Service
            if (!selectedService.checkId(selectedServices)){
                appointment.addService(selectedService)
                selectedServices.add(selectedService)
                viewModel.setCurAppointment(appointment)
                Log.i("service change", appointment.toString())
            }
            getChips()

        }

        //Customers selection
        binding.btnEditAppCustomer.setOnClickListener {
            val selectedCustomer = binding.editAppCustomer.selectedItem as Customer
            if (!selectedCustomer.checkId(selectedCustomers)){
                appointment.addCustomer(selectedCustomer)
                selectedCustomers.add(selectedCustomer)
                viewModel.setCurAppointment(appointment)
                Log.i("customer change", appointment.toString())
            }
            getChips()
        }

        //Employees selection
        binding.btnEditAppEmployee.setOnClickListener {
            val selectedEmployee = binding.editAppEmployee.selectedItem as Employee
            if (!selectedEmployee.checkId(selectedEmployees)){
                appointment.addEmployee(selectedEmployee)
                selectedEmployees.add(selectedEmployee)
                viewModel.setCurAppointment(appointment)
                Log.i("customer change", appointment.toString())
            }
            getChips()
        }


        //date selector
        binding.tfDate.setEndIconOnClickListener {
            val cal = Calendar.getInstance()
            val year: Int = cal.get(Calendar.YEAR)
            val month: Int = cal.get(Calendar.MONTH)
            val day: Int = cal.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }

        //time selector
        binding.tfTime.setEndIconOnClickListener {
            val cal = Calendar.getInstance()
            val hour: Int = cal.get(Calendar.HOUR)
            val minute: Int = cal.get(Calendar.MINUTE)
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_newAppointmentFragment_to_calenderFragment)
        }

        binding.btnSubmit.setOnClickListener {
            var duration = 0
            if (selectedServices.size > 0){
                for (i in selectedServices){
                    if (i.duration != null){
                        duration += i.duration!!
                    }else{
                        duration += 1
                    }
                }
            }

            appointment.comment = binding.editAppTxtComment.text.toString()
            appointment.duration = duration

            if (appointment.date != null && appointment.timeindex != null){
                if (isEdit){
                    Log.i("UpdatedCustomer", appointment.toString())
                    viewModel.updateAppointment(appointment)
                }else{
                    Log.i("NewCustomer", appointment.toString())
                    viewModel.newAppointment(appointment)
                }
                findNavController().navigate(R.id.action_newAppointmentFragment_to_calenderFragment)
            }
        }

        viewModel.services.observe(viewLifecycleOwner, {
            serviceList.clear()
            serviceList.addAll(it)
            updateChoices()
        })
        viewModel.customers.observe(viewLifecycleOwner, {
            customerList.clear()
            customerList.addAll(it)
            updateChoices()
        })

        viewModel.employees.observe(viewLifecycleOwner, {
            employeeList.clear()
            employeeList.addAll(it)
            updateChoices()
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it){
                binding.newAProgressBar.visibility = View.VISIBLE
            }else{
                binding.newAProgressBar.visibility = View.GONE
            }
        })

        //Api error handling
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            if (it == null){
                Log.i("ErrorMessageTest", "No errors")
            } else {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        if (appointment.date != null){
            textDate.text = tools.formatDate(appointment.date!!)
        }
        if (appointment.timeindex != null){
            textTime.text = tools.timeIndexFormat(appointment.timeindex!!)
        }
        getChips()
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayofmonth: Int) {
        val cal = Calendar.getInstance()
        cal.clear()
        cal.set(year,month,dayofmonth)
        val date = Date(cal.timeInMillis)
        appointment.date = date
        textDate.text = tools.formatDate(date)
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        val timeindex = (hour*60) + minute
        textTime.text = tools.timeIndexFormat(timeindex)
        appointment.timeindex = timeindex
    }

    private fun getChips(){
        binding.CGCustomers.removeAllViews()
        binding.CGEmployees.removeAllViews()
        binding.CGServices.removeAllViews()
        for (i in selectedCustomers){
            binding.CGCustomers.addView(addChip(i))
        }
        for (i in selectedEmployees){
            binding.CGEmployees.addView(addChip(i))
        }
        for (i in selectedServices){
            binding.CGServices.addView(addChip(i))
        }
        updateChoices()
    }

    private fun addChip(item: Any): Chip {
        val chip = Chip(requireContext())
        chip.text = item.toString()
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            when (item) {
                is Service -> {
                    selectedServices.remove(item)
                    appointment.removeService(item)
                }
                is Employee -> {
                    selectedEmployees.remove(item as Employee)
                    appointment.removeEmployee(item)
                }
                is Customer -> {
                    selectedCustomers.remove(item as Customer)
                    appointment.removeCustomer(item)
                }
            }
            getChips()
        }
        chip.setChipBackgroundColorResource(R.color.primary)
        return chip
    }

    private fun updateChoices(){
        availableServices = serviceList.filter { !selectedServices.contains(it) } as MutableList<Service>
        SPService.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, availableServices)
        availableEmployees = employeeList.filter { !selectedEmployees.contains(it) } as MutableList<Employee>
        SPEmployee.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, availableEmployees)
        availableCustomers = customerList.filter { !selectedCustomers.contains(it) } as MutableList<Customer>
        SPCustomer.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, availableCustomers)
    }

}