package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.format.DateUtils
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentCalenderBinding
import no.usn.gruppe4.crmwebappandroid.models.appointment.*
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import java.util.*


class CalenderFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: CalanderViewModel
    private lateinit var binding: FragmentCalenderBinding
    private val appointmentList = mutableListOf<Appointment>()
    private var selectedDate = Date()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentCalenderBinding.inflate(inflater)
        //set the viewModel
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)
        viewModel.getMyAppointmentsDate("602a7f4891d34d18402f4e44", System.currentTimeMillis())

        //val myDataset = getAppointmentList()
        val adapter = AppointmentAdapter(requireContext(), appointmentList)
        val headerAdapter = AppointmentHeaderAdapter(selectedDate)
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


        adapter.setOnItemClickListener(object: AppointmentAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                /*
                val bundle = Bundle()
                bundle.putString("appointment", appointmentList[position]._id)
                findNavController().navigate(R.id.action_calenderFragment_to_appointmentClicked, bundle)

                 */
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


}