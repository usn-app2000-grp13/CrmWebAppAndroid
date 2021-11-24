package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentCalenderBinding
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.*
import no.usn.gruppe4.crmwebappandroid.models.login.SecSharePref
import no.usn.gruppe4.crmwebappandroid.models.login.SharedPrefInterface
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import no.usn.gruppe4.crmwebappandroid.uicomponents.MainActivity
import java.util.*


class CalenderFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: CalanderViewModel
    private lateinit var binding: FragmentCalenderBinding
    private val appointmentList = mutableListOf<Appointment>()
    private var selectedDate = Date()
    private lateinit var sharedPreferences: SharedPrefInterface

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentCalenderBinding.inflate(inflater)
        //set the viewModel
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)
        viewModel.getMyAppointmentsDate("602a7f4891d34d18402f4e44", System.currentTimeMillis())

        sharedPreferences = SecSharePref(requireContext(), "secrets")
        val username = sharedPreferences.get("name")
        val id = sharedPreferences.get("id")
        viewModel.getMyAppointmentsDate(id, System.currentTimeMillis())
        //val myDataset = getAppointmentList()
        val adapter = AppointmentAdapter(requireContext(), appointmentList)
        val headerAdapter = AppointmentHeaderAdapter(selectedDate, username)
        val concatAdapter = ConcatAdapter(headerAdapter, adapter)



        binding.recyclerView.adapter = concatAdapter

        viewModel.appointment.observe(viewLifecycleOwner, { appointments ->
            appointmentList.clear()
            val appointmentIterator = appointments.iterator();
            while (appointmentIterator.hasNext()){
                val app = appointmentIterator.next()
                if (app.checkDate(selectedDate)){
                    appointmentList.add(app)
                }
            }
            //appointmentList.addAll(appointments)
            appointmentList.sortBy { it.timeindex }
            adapter.notifyDataSetChanged()
            binding.recyclerView.scheduleLayoutAnimation()
        })

        viewModel.date.observe(viewLifecycleOwner, {
            headerAdapter.curDate = headerAdapter.setCurDate(it)
            try {
                appointmentList.clear()
                val appointmentIterator = viewModel.appointment.value!!.iterator();
                while (appointmentIterator.hasNext()){
                    val app = appointmentIterator.next()
                    if (app.checkDate(selectedDate)){
                        appointmentList.add(app)
                    }
                }
            }catch (e: Exception){

            }

            adapter.notifyDataSetChanged()
        })

        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = true
            viewModel.getMyAppointmentsDate(id, System.currentTimeMillis())
            Log.i("refresh called", "Refreshing")
            binding.swipeLayout.isRefreshing = false
        }

        adapter.setOnItemClickListener(object: AppointmentAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putParcelable("appointment", appointmentList[position])
                findNavController().navigate(R.id.action_calenderFragment_to_appointmentClicked, bundle)
            }

            override fun onDeleteClick(position: Int) {
                //delete from list
                Log.i("Delete button holder: ", "delete button pressed for position $position")
                viewModel.removeAppointment(IdRequest(appointmentList.get(position)._id))
                viewModel.getMyAppointmentsDate("602a7f4891d34d18402f4e44", System.currentTimeMillis())
            }

            override fun onEditClick(position: Int) {
                Log.i("Edit button holder: ", "Edit button pressed for position $position")
            }

            override fun onMessageClick(position: Int) {
                Log.i("Message button holder: ", "Message button pressed for position $position")
            }

            override fun onExpCusClick(position: Int) {
                TODO("Not yet implemented")
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






}