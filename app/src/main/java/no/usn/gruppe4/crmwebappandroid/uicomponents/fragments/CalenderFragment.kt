package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentCalenderBinding
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.*
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.login.SecSharePref
import no.usn.gruppe4.crmwebappandroid.models.login.SharedPrefInterface
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import java.util.*

private const val TAG = "CalenderFragment"
class CalenderFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: CalanderViewModel
    private lateinit var binding: FragmentCalenderBinding
    private val appointmentList = mutableListOf<Appointment>()
    private lateinit var selectedDate: Date
    private lateinit var sharedPreferences: SharedPrefInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null){
            arguments?.getLong("selectedDate").let { el->
                selectedDate = Date(el!!)
            }
        }else{
            selectedDate = Date()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentCalenderBinding.inflate(inflater)
        //set the viewModel
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)

        sharedPreferences = SecSharePref(requireContext(), "secrets")
        val username = sharedPreferences.get("name")
        val id = sharedPreferences.get("id")
        viewModel.getMyAppointmentsDate(id, System.currentTimeMillis().minus(2629800000))
        //val myDataset = getAppointmentList()
        val adapter = AppointmentAdapter(requireContext(), appointmentList)
        val headerAdapter = AppointmentHeaderAdapter(selectedDate, username)
        val concatAdapter = ConcatAdapter(headerAdapter, adapter)
        viewModel.changeDate(selectedDate)


        binding.recyclerView.adapter = concatAdapter

        viewModel.appointment.observe(viewLifecycleOwner, { appointments ->
            appointmentList.clear()
            val appointmentIterator = appointments.iterator()
            while (appointmentIterator.hasNext()){
                val app = appointmentIterator.next()
                if (app.customers?.get(0)?._customer != null){
                    if (app.checkDate(selectedDate)){
                        appointmentList.add(app)
                    }
                }
            }
            //appointmentList.addAll(appointments)
            appointmentList.sortBy { it.timeindex }
            Log.i(TAG, appointmentList.toString())
            adapter.notifyDataSetChanged()
            binding.recyclerView.scheduleLayoutAnimation()
        })

        viewModel.date.observe(viewLifecycleOwner, {
            headerAdapter.curDate = headerAdapter.setCurDate(it)
            try {
                selectedDate = it
                getCorrectAppointments(it)
                adapter.notifyDataSetChanged()
            }catch (e: Exception){

            }


        })

        //Api error handling
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            if (it == null){
                binding.errorImage.visibility = View.GONE
                Log.i("ErrorMessageTest", "No errors")
            } else {
                binding.errorImage.visibility = View.VISIBLE
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        //Api call loading
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it == false){
                binding.calenderProgress.visibility = View.GONE
            }else {
                binding.calenderProgress.visibility = View.VISIBLE
            }
        })
        //On swipe down refresh
        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = true
            viewModel.getMyAppointmentsDate(id, System.currentTimeMillis().minus(31560000000))
            Log.i("refresh called", "Refreshing")
            binding.swipeLayout.isRefreshing = false
            getCorrectAppointments(selectedDate)
        }

        //list item click inspect appointment
        adapter.setOnItemClickListener(object: AppointmentAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putParcelable("appointment", appointmentList[position])
                findNavController().navigate(R.id.action_calenderFragment_to_appointmentClicked, bundle)
            }
        })

        binding.recyclerView.setHasFixedSize(true)

        binding.fabNewAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_calenderFragment_to_newAppointmentFragment)
        }
        binding.fabChooseDate.setOnClickListener {
            var today = Calendar.getInstance()
            today.time = selectedDate;
            val year: Int? = today?.get(Calendar.YEAR)
            val month: Int? = today?.get(Calendar.MONTH)
            val day: Int? = today?.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(), this, year!!, month!!, day!!).show()
        }
        return binding.root
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayofmonth: Int) {
        val cal = Calendar.getInstance()
        cal.clear()
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DATE, dayofmonth)
        selectedDate = cal.time
        Log.i("date", "$selectedDate")
        viewModel.changeDate(selectedDate)
    }

    fun deleteAppointment(appointment: Appointment){
        viewModel.removeAppointment(IdRequest(appointment._id))
    }
    private fun createErrorDialog(message: String) {
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton("Close", DialogInterface.OnClickListener {
                    dialogInterface, i -> requireActivity().finish()
            })
        val alert = builder.create()
        alert.show()
    }

    private fun getCorrectAppointments(date: Date){
        appointmentList.clear()
        val appointmentIterator = viewModel.appointment.value!!.iterator()
        while (appointmentIterator.hasNext()){
            val app = appointmentIterator.next()
            if (app.customers?.get(0)?._customer != null) {
                if (app.checkDate(date)) {
                    appointmentList.add(app)
                }
            }
        }
        Log.i(TAG, "Appointments: $appointmentList")
    }







}