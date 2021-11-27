package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentLandingBinding
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.login.SecSharePref
import no.usn.gruppe4.crmwebappandroid.models.login.SharedPrefInterface
import no.usn.gruppe4.crmwebappandroid.models.stats.employeePop
import no.usn.gruppe4.crmwebappandroid.models.stats.servicePop
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


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
            appointmentList.sortBy { it.timeindex }
        })

        viewModel.nrTodo.observe(viewLifecycleOwner, { todoCount ->
            nrTodo = todoCount
            binding.txtTodoCount.text = nrTodo.toString()
        })

        viewModel.statEmployee.observe(viewLifecycleOwner, {
            val empPopData = mutableListOf<MyChartData>()
            for (i in it){
                if (i.employee.isNotEmpty()){
                    empPopData.add(MyChartData(i.employee.toString(), i.count))
                }
            }
            makePieChart(empPopData, "Employees", binding.piechart)
        })

        viewModel.statService.observe(viewLifecycleOwner, {
            val serPopData = mutableListOf<MyChartData>()
            for (i in it){
                if (i.service.isNotEmpty()){
                    serPopData.add(MyChartData(i.service.toString(), i.count))
                }
            }
            makePieChart(serPopData, "Services", binding.piechartservice)
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

    data class MyChartData(
        val label: String,
        val amount: Int,
    )

    //Lager en pie chart ut av key value list
    fun makePieChart(data: List<MyChartData>, title: String, pieChart: PieChart){
        //chart data
        val piechartListfin = ArrayList<Entry>()
        val pieChartnames = ArrayList<String>()
        val colorlist = ArrayList<Int>()
        colorlist.add(Color.parseColor("#304567"))
        colorlist.add(Color.parseColor("#309967"))
        colorlist.add(Color.parseColor("#476567"))
        colorlist.add(Color.parseColor("#890567"))
        colorlist.add(Color.parseColor("#a35567"))
        colorlist.add(Color.parseColor("#ff5f67"))
        colorlist.add(Color.parseColor("#3ca567"))
        var curindex = 0
        for (i in data){
            pieChartnames.add(i.label)
            piechartListfin.add(Entry(i.amount.toFloat(), curindex))
            curindex++
        }
        val pieDataSet = PieDataSet(piechartListfin, title)
        pieDataSet.setColors(colorlist)
        val pieData = PieData(pieChartnames, pieDataSet)
        pieChart.data = pieData

        pieChart.setDrawXValues(true)
        pieChart.setDrawYValues(true)
        pieChart.setValueTextColor(Color.BLUE)
        pieChart.centerText = "Popularity"
        pieChart.invalidate()
        pieChart.animate()
    }


}