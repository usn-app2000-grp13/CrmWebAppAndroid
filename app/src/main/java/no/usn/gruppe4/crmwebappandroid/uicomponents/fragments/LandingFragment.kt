package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentLandingBinding
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.login.SecSharePref
import no.usn.gruppe4.crmwebappandroid.models.login.SharedPrefInterface
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import java.util.*


class LandingFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentLandingBinding
    private lateinit var viewModel: CalanderViewModel
    private lateinit var sharedPreferences: SharedPrefInterface
    private var nrTodo = 0
    private var nrApp = 0
    private lateinit var id: String
    private var selectedDate = Calendar.getInstance()
    val appointmentList = mutableListOf<Appointment>()
    val employeeList = arrayListOf<Employee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLandingBinding.inflate(inflater)

        sharedPreferences = SecSharePref(requireContext(), "secrets")
        val username = sharedPreferences.get("name")
        id = sharedPreferences.get("id")
        binding.txtUserName.text = username
        //set the viewModel
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)
        viewModel.getEmployees()
        viewModel.employees.observe(viewLifecycleOwner, { employees ->
            employeeList.clear()
            employeeList.addAll(employees)
        })

        viewModel.getMyAppointmentsDate(id, System.currentTimeMillis())
        viewModel.getTodoCount()
        viewModel.getServicePop()
        viewModel.getEmployeePop()


        viewModel.appointment.observe(viewLifecycleOwner, { appointments ->
            val todayDate = Date()
            appointmentList.clear()
            val appointmentIterator = appointments.iterator();
            while (appointmentIterator.hasNext()){
                val app = appointmentIterator.next()
                if (app.checkDate(todayDate) && !app.customers.isNullOrEmpty()){
                    appointmentList.add(app)
                }
            }
            nrApp = appointmentList.size
            Log.i("appointments in list", " $appointmentList")
            binding.txtAppointmentToday.text = nrApp.toString()
            binding.listAppointmentsToday.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, appointmentList)
            appointmentList.sortBy { it.timeindex }
        })

        viewModel.nrTodo.observe(viewLifecycleOwner, { todoCount ->
            nrTodo = todoCount
            binding.txtTodoCount.text = nrTodo.toString()
        })



        binding.appsTodayContainer.setOnClickListener {
            goToSelectedDate(System.currentTimeMillis())
        }
        binding.addContainer.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_newAppointmentFragment)
        }
        binding.findContainer.setOnClickListener {
            val year: Int? = selectedDate?.get(Calendar.YEAR)
            val month: Int? = selectedDate?.get(Calendar.MONTH)
            val day: Int? = selectedDate?.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(), this, year!!, month!!, day!!).show()
        }
        binding.todoTodayContainer.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_todoFragment)
        }
        binding.historyContainer.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_appointmentHistoryFragment)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayofmonth: Int) {
        val cal = Calendar.getInstance()
        cal.clear()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DATE, dayofmonth)
        selectedDate = cal
        goToSelectedDate(cal.timeInMillis)
    }

    fun goToSelectedDate(millis: Long){
        findNavController().navigate(R.id.action_landingFragment_to_calenderFragment)
    }


}