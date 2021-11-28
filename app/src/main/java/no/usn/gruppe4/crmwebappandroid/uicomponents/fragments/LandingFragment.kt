package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.PieChart
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
import no.usn.gruppe4.crmwebappandroid.models.mail.MailRequest
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import java.util.*
import kotlin.collections.ArrayList

class LandingFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentLandingBinding
    private lateinit var viewModel: CalanderViewModel
    private lateinit var sharedPreferences: SharedPrefInterface
    private var nrTodo = 0
    private var nrApp = 0
    private lateinit var id: String
    private var selectedDate = Calendar.getInstance()
    private val appointmentList = mutableListOf<Appointment>()
    private val ratingList = mutableListOf<Appointment>()
    private val employeeList = arrayListOf<Employee>()
    private val customerList = mutableListOf<Customer>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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

        viewModel.getCustomers()
        viewModel.customers.observe(viewLifecycleOwner, { customers ->
            customerList.clear()
            customerList.addAll(customers)
        })

        viewModel.getMyAppointmentsDate(id, System.currentTimeMillis().minus(31556952000))
        viewModel.getTodoCount()
        viewModel.getServicePop()
        viewModel.getEmployeePop()

        //view model observers
        viewModel.appointment.observe(viewLifecycleOwner, { appointments ->
            val todayDate = Date()
            appointmentList.clear()
            ratingList.addAll(appointments)
            calculateRating()
            val appointmentIterator = appointments.iterator()
            while (appointmentIterator.hasNext()){
                val app = appointmentIterator.next()
                if (app.checkDate(todayDate)){
                    appointmentList.add(app)
                }
            }
            nrApp = appointmentList.size
            binding.txtAppointmentToday.text = nrApp.toString()
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
            makePieChart(empPopData, getString(R.string.employee), binding.piechart)
        })

        viewModel.statService.observe(viewLifecycleOwner, {
            val serPopData = mutableListOf<MyChartData>()
            for (i in it){
                if (i.service.isNotEmpty()){
                    serPopData.add(MyChartData(i.service.toString(), i.count))
                }
            }
            makePieChart(serPopData, getString(R.string.service), binding.piechartservice)
        })


        //button handlers

        binding.appsTodayContainer.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_calenderFragment)
        }
        binding.addContainer.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_newAppointmentFragment)
        }
        binding.findContainer.setOnClickListener {
            val year: Int = selectedDate.get(Calendar.YEAR)
            val month: Int = selectedDate.get(Calendar.MONTH)
            val day: Int = selectedDate.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        binding.todoTodayContainer.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_todoFragment)
        }
        binding.historyContainer.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_appointmentHistoryFragment)
        }
        binding.msgContainer.setOnClickListener {
            showMailDialog()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    //on date sett for datepicker sette valgt dato og går til den dato
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayofmonth: Int) {
        val cal = Calendar.getInstance()
        cal.clear()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DATE, dayofmonth)
        selectedDate = cal
        goToSelectedDate(cal.timeInMillis)
    }

    //går til selected date fix denne
    private fun goToSelectedDate(millis: Long){
        val bundle = Bundle()
        bundle.putLong("selectedDate", millis)
        findNavController().navigate(R.id.action_landingFragment_to_calenderFragment, bundle)
    }

    //lite data class for å sende pie chart data
    data class MyChartData(
        val label: String,
        val amount: Int,
    )

    //Lager en pie chart ut av key value list
    private fun makePieChart(data: List<MyChartData>, title: String, pieChart: PieChart){
        //chart data
        val pieChartListfin = ArrayList<Entry>()
        val pieChartNames = ArrayList<String>()
        val colorList = ArrayList<Int>()
        colorList.add(Color.parseColor("#56e2cf"))
        colorList.add(Color.parseColor("#e25668"))
        colorList.add(Color.parseColor("#8a56e2"))
        colorList.add(Color.parseColor("#aee256"))
        colorList.add(Color.parseColor("#e2cf56"))
        colorList.add(Color.parseColor("#5668e2"))
        colorList.add(Color.parseColor("#e28956"))
        colorList.add(Color.parseColor("#cf56e2"))
        colorList.add(Color.parseColor("#68e256"))
        for ((curIndex, i) in data.withIndex()){
            pieChartNames.add(i.label)
            pieChartListfin.add(Entry(i.amount.toFloat(), curIndex))
        }
        val pieDataSet = PieDataSet(pieChartListfin, title)
        pieDataSet.colors = colorList
        val pieData = PieData(pieChartNames, pieDataSet)
        pieChart.data = pieData
        pieChart.setDrawXValues(true)

        pieChart.setDrawYValues(true)
        pieChart.setValueTextColor(Color.BLUE)
        pieChart.setUsePercentValues(true)
        pieChart.setDrawLegend(false)
        pieChart.invalidate()
        pieChart.animate()
    }

    //lager en mail dialog for å lage en mail
    private fun showMailDialog(){
        var msg: String
        var recipient: String
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.message_dialog_w_choice, null)
        val textBox = dialogLayout.findViewById<EditText>(R.id.ETmessage)
        val spinner = dialogLayout.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, customerList)
        builder.setTitle(getString(R.string.sendEmailToCustomer)).setPositiveButton(getString(R.string.btnSend)){ _, _ ->
            msg = textBox.text.toString()
            val cust = spinner.selectedItem as Customer
            recipient = cust.email!!
            sendEmails(msg, recipient)
        }.setNegativeButton(getString(R.string.btnCancel)){ _, _ ->

        }.setView(dialogLayout)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.gradientbackground)
        dialog.show()
    }

    //sender epost etter mail dialogen
    private fun sendEmails(message: String, email: String){
        viewModel.sendUserMail(MailRequest(message, message, email, "Message", "Test", email))
        Toast.makeText(requireContext(), getString(R.string.messageSendSuccess), Toast.LENGTH_SHORT).show()
    }

    //beregner rating for ansatt
    private fun calculateRating(){
        var numberOfRatings = 0
        var totalRating = 0.0
        var myRating = 0
        for (i in ratingList){
            if (!i.ratings.isNullOrEmpty()){
                for (j in i.ratings){
                    numberOfRatings++
                    totalRating += j.rating

                }
            }
        }
        if (numberOfRatings > 0){
            myRating = (totalRating / numberOfRatings).toInt()
        }
        when(myRating){
            1 -> {
                binding.filledStar1.visibility = View.VISIBLE
            }
            2 -> {
                binding.filledStar1.visibility = View.VISIBLE
                binding.filledStar2.visibility = View.VISIBLE
            }
            3 -> {
                binding.filledStar1.visibility = View.VISIBLE
                binding.filledStar2.visibility = View.VISIBLE
                binding.filledStar3.visibility = View.VISIBLE
            }
            4 -> {
                binding.filledStar1.visibility = View.VISIBLE
                binding.filledStar2.visibility = View.VISIBLE
                binding.filledStar3.visibility = View.VISIBLE
                binding.filledStar4.visibility = View.VISIBLE
            }
            5 -> {
                binding.filledStar1.visibility = View.VISIBLE
                binding.filledStar2.visibility = View.VISIBLE
                binding.filledStar3.visibility = View.VISIBLE
                binding.filledStar4.visibility = View.VISIBLE
                binding.filledStar5.visibility = View.VISIBLE
            }
        }
    }


}