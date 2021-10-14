package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class CalenderFragment : Fragment() {

    private lateinit var viewModel: CalanderViewModel
    private lateinit var binding: FragmentCalenderBinding
    private val appointmentList = mutableListOf<Appointment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentCalenderBinding.inflate(inflater)
        //set the viewModel
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)
        val date = Date()
        Log.i("Date ", "$date")
        viewModel.getMyAppointmentsDate("602a7f4891d34d18402f4e44", correctDate())
        //val myDataset = getAppointmentList()
        val adapter = AppointmentAdapter(requireContext(), appointmentList)
        val headerAdapter = AppointmentHeaderAdapter(date)
        val concatAdapter = ConcatAdapter(headerAdapter, adapter)

        binding.recyclerView.adapter = concatAdapter

        viewModel.appointment.observe(viewLifecycleOwner, { appointments ->
            appointmentList.clear()
            val appointmentIterator = appointments.iterator();
            while (appointmentIterator.hasNext()){
                val app = appointmentIterator.next()
                if (DateUtils.isToday(app.date!!.time)){
                    appointmentList.add(app)
                }
            }
            //appointmentList.addAll(appointments)
            appointmentList.sortBy { it.timeindex }
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
        return binding.root
    }

    private fun correctDate(): Date{
        val newDate = Date(System.currentTimeMillis()-24*60*60*1000)
        Log.i("Date", newDate.toString())
        return newDate
    }

}