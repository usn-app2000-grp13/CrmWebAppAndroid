package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentAppointmentClickedBinding
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.mail.MailRequest
import no.usn.gruppe4.crmwebappandroid.models.mail.RatingRequest
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "AppointmentClicked"

class AppointmentClicked : Fragment() {

    private var appointment: Appointment? = null
    lateinit var binding: FragmentAppointmentClickedBinding
    private lateinit var viewModel: CalanderViewModel
    private lateinit var chipGroup: ChipGroup
    private var deleted = false
    private var tmpServices: MutableList<Service> = mutableListOf()
    private var serviceList = mutableListOf<Service>()
    private var tmpCustomers: MutableList<Customer> = mutableListOf()
    private var customerList = mutableListOf<Customer>()
    private var tmpEmployees: MutableList<Employee> = mutableListOf()
    private var employeeList = mutableListOf<Employee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Appointment>("appointment").let { el->
            appointment = el
        }

        appointment?.customers?.forEach {
            tmpCustomers.add(it._customer!!)
        }
        appointment?.employees?.forEach {
            tmpEmployees.add(it._employee!!)
        }
        appointment?.services?.forEach {
            tmpServices.add(it._service!!)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAppointmentClickedBinding.inflate(inflater)

        //initialize viemodel
        viewModel = ViewModelProvider(this).get(CalanderViewModel::class.java)
        //initialize lists for edit appointment
        viewModel.getServices()
        viewModel.services.observe(viewLifecycleOwner, {
            serviceList.addAll(it)
        })
        viewModel.getCustomers()
        viewModel.customers.observe(viewLifecycleOwner, {
            customerList.addAll(it)
        })
        viewModel.getEmployees()
        viewModel.employees.observe(viewLifecycleOwner, {
            employeeList.addAll(it)
        })

        if (appointment!!.checkDatePast(Date(System.currentTimeMillis()))){
            binding.btnSendRating.isEnabled = true
            binding.btnSendRating.setOnClickListener {
                viewModel.sendRatingRequest(RatingRequest(appointment!!._id!!))
            }
        }
        chipGroup = binding.customerChips
        Log.i(TAG, "appointment Received: ${appointment.toString()}")

        binding.txtTime.setText(timeIndexFormat(appointment?.timeindex!!))
        binding.txtDate.setText(SimpleDateFormat("yyyy-MM-dd").format(appointment?.date))
        if (appointment?.comment.toString().isEmpty()){
            binding.txtNotes.setText("")
        }else{
            binding.txtNotes.setText(appointment?.comment.toString())
        }


        binding.btnDeleteAppointment.setOnClickListener {
            viewModel.removeAppointment(IdRequest(appointment?._id))
            deleted = true
        }

        binding.btnEditAppointment.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("appointment", appointment)
            findNavController().navigate(R.id.action_appointmentClicked_to_newAppointmentFragment, bundle)
        }

        binding.btnSendMessage.setOnClickListener {
            showMailDialog()
        }

        getChips()
        viewModel.status.observe(viewLifecycleOwner, {
            if (it){
                if (deleted){
                    findNavController().navigate(R.id.action_appointmentClicked_to_calenderFragment)
                }
                binding.mainContainer.visibility = View.VISIBLE
                binding.progressBar2.visibility = View.GONE
            }else{
                binding.mainContainer.visibility = View.GONE
                binding.progressBar2.visibility = View.VISIBLE
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun timeIndexFormat(timeindex: Int): String{
        var res = ""
        val clockM = timeindex % 60
        val clockH = (timeindex - clockM) / 60

        if (clockH < 10){
            res += "0$clockH:"
        }else{
            res += "$clockH:"
        }
        if (clockM < 10) {
            res += "0$clockM"
        }else{
            res += "$clockM"
        }
        return res
    }
    private fun getChips(){
        for (i in tmpCustomers){
            binding.customerChips.addView(addChip(i.toString()))
        }
        for (i in tmpEmployees){
            binding.employeeChips.addView(addChip(i.toString()))
        }
        for (i in tmpServices){
            binding.serviceChips.addView(addChip(i.toString()))
        }
    }

    private fun addChip(text:String):Chip {
        val chip = Chip(requireContext())
        chip.text = text
        chip.isCloseIconVisible = true
        chip.setChipBackgroundColorResource(R.color.primary)
        return chip
    }

    private fun showMailDialog(){
        var msg = ""
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.mail_dialog, null)
        val textBox = dialogLayout.findViewById<EditText>(R.id.txtMail)

        builder.setTitle("Send mail to user").setPositiveButton("Send"){ _, _ ->
            msg = textBox.text.toString()
            sendEmails(msg)
        }.setNegativeButton("Cancel"){ _, _ ->

            Log.i(TAG, "Pressed cancel")
        }.setView(dialogLayout)

        val dialog = builder.create()
        dialog.show()
    }

    private fun sendEmails(message: String){
        for (i in tmpCustomers){
            val customer = findCustomers(i._id!!)
            val email = customer!!.email
            Log.i("epost", "send to: $email")
            viewModel.sendUserMail(MailRequest(message, message, email!!, "Appointment", "Test", email!!))
            Toast.makeText(requireContext(), "Message send!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun findCustomers(id: String):Customer?{
        for (i in customerList){
            if (i._id.equals(id))
                return i
        }
        return null
    }


}