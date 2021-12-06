package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.employee

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewEmployeeBinding
import no.usn.gruppe4.crmwebappandroid.models.employee.Address
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [NewEmployeeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewEmployeeFragment : Fragment() {
    lateinit var binding: FragmentNewEmployeeBinding
    lateinit var viewModel: EmployeeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewEmployeeBinding.inflate(inflater)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            findNavController().popBackStack()
        }
        // Inflate the layout for this fragment
        binding.neCancel.setOnClickListener { findNavController().popBackStack() }

        binding.neSubmit.setOnClickListener {
            viewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
            var ok = true

            if (!isFilled(binding.txtNeFirstName)) ok = false
            if (!isFilled(binding.txtNeLastName)) ok = false
            if (!isFilled(binding.txtNeEmail)) ok = false
            if (!isFilled(binding.txtNePassword)) ok = false
            if (!checkEmail(binding.txtNeEmail)) ok = false;

            if (ok) {
                val firstname = binding.txtNeFirstName.text?.toString()
                val lastname = binding.txtNeLastName.text?.toString()
                val phone = binding.txtNePhone.text?.toString()
                val email = binding.txtNeEmail.text?.toString()
                val password = binding.txtNePassword.text?.toString()
                val street = binding.txtNeStreet.text?.toString()
                val streetNumber = binding.txtNeStreetNumber.text?.toString()
                val postArea = binding.txtNePostArea.text?.toString()
                val apartment = binding.txtNeApartment.text?.toString()
                val postCode = binding.txtNePostCode.text?.toString()

                val newAddress = Address(
                    postArea = postArea, apartment = apartment,
                    postCode = postCode, street = street, streetNumber = streetNumber,
                )

                val employee = Employee(
                    _id = null, firstname = firstname, lastname = lastname, phone = phone,
                    email = email, password = password, address = newAddress,
                    active = true, __v = 1, _vendor = null, createdAt = null,
                    level = 1, updatedAt = null
                )

                viewModel.newEmployee(employee)
                var test = false

                //check if success is true on the api call
                viewModel.bool.observe(viewLifecycleOwner, {
                    test = it
                    //if success is true
                    if (test) {
                        findNavController().popBackStack()
                    }
                    // if it remains false
                    else {
                        val text = "Api has not accepted the request yet!"
                        val duration = Toast.LENGTH_SHORT
                        val toast = Toast.makeText(context, text, duration)
                        toast.show()
                    }
                })

                //
                if (!test) {
                    val text = "Api error!"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(context, text, duration)
                    toast.show()
                }


            }


        }



        return binding.root


    }

    private fun isFilled(element: TextInputEditText): Boolean {
        var res: Boolean
        if (element.text!!.isEmpty()) {
            element.error = "Required!"
            res = false
        } else {
            res = true
        }
        return res;
    }

    private fun checkEmail(element: TextInputEditText): Boolean {
        var res: Boolean
        /* https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/-regex/ */
        val emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        res = element.text!!.matches(Regex(emailPattern))
        if(!res){
            element.error = "invalid email address"
        }

        return res
    }
}