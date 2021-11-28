package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.nfc.Tag
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import no.usn.gruppe4.crmwebappandroid.models.mail.MailRequest
import no.usn.gruppe4.crmwebappandroid.models.stats.employeePop
import no.usn.gruppe4.crmwebappandroid.models.stats.servicePop
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

private const val TAG = "LandingFragment"
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
    val customerList = mutableListOf<Customer>()

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

        viewModel.getCustomers()
        viewModel.customers.observe(viewLifecycleOwner, { customers ->
            customerList.clear()
            customerList.addAll(customers)
        })

        viewModel.getMyAppointmentsDate(id, System.currentTimeMillis())
        viewModel.getTodoCount()
        viewModel.getServicePop()
        viewModel.getEmployeePop()


        viewModel.appointment.observe(viewLifecycleOwner, { appointments ->
            val todayDate = Date()
            appointmentList.clear()
            appointmentList.addAll(appointments)
            calculateRating()
            appointmentList.clear()
            val appointmentIterator = appointments.iterator();
            while (appointmentIterator.hasNext()){
                val app = appointmentIterator.next()
                if (app.checkDate(todayDate)){
                    appointmentList.add(app)
                }
            }
            nrApp = appointmentList.size
            binding.txtAppointmentToday.text = nrApp.toString()
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
        binding.msgContainer.setOnClickListener {
            showMailDialog()
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
        colorlist.add(Color.parseColor("#56e2cf"))
        colorlist.add(Color.parseColor("#e25668"))
        colorlist.add(Color.parseColor("#8a56e2"))
        colorlist.add(Color.parseColor("#aee256"))
        colorlist.add(Color.parseColor("#e2cf56"))
        colorlist.add(Color.parseColor("#5668e2"))
        colorlist.add(Color.parseColor("#e28956"))
        colorlist.add(Color.parseColor("#cf56e2"))
        colorlist.add(Color.parseColor("#68e256"))
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
        pieChart.setUsePercentValues(true)
        pieChart.setDrawLegend(false)
        pieChart.invalidate()
        pieChart.animate()
    }

    private fun showMailDialog(){
        var msg = ""
        var recipient = ""
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.message_dialog_w_choice, null)
        val textBox = dialogLayout.findViewById<EditText>(R.id.ETmessage)
        val spinner = dialogLayout.findViewById<Spinner>(R.id.spinner)
        spinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item, customerList)
        builder.setTitle("Send mail to user").setPositiveButton("Send"){dialog, which ->
            msg = textBox.text.toString()
            val cust = spinner.selectedItem as Customer
            recipient = cust.email!!
            sendEmails(msg, recipient)
        }.setNegativeButton("Cancel"){dialog, which ->

        }.setView(dialogLayout)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.gradientbackground)
        dialog.show()
    }

    fun sendEmails(message: String, email: String){
        Log.i("epost", "send to: $email")
        viewModel.sendUserMail(MailRequest(message, message, email, "Message", "Test", email))
        Toast.makeText(requireContext(), "Message send!", Toast.LENGTH_SHORT).show()
    }

    fun calculateRating(){
        var numberOfRatings = 0
        var totalRating = 0.0
        var myRating = 0
        for (i in appointmentList){
            if (!i.ratings.isNullOrEmpty()){
                for (i in i.ratings){
                    numberOfRatings++
                    totalRating += i.rating
                    Log.i(TAG, "rating found! $numberOfRatings $totalRating")
                }
            }
        }
        if (numberOfRatings > 0){
            myRating = (totalRating / numberOfRatings).toInt()
        }
        when(myRating){
            1 -> binding.filledStar1.visibility = View.VISIBLE
            2 -> binding.filledStar2.visibility = View.VISIBLE
            3 -> binding.filledStar3.visibility = View.VISIBLE
            4 -> binding.filledStar4.visibility = View.VISIBLE
            5 -> binding.filledStar5.visibility = View.VISIBLE
        }
    }


}