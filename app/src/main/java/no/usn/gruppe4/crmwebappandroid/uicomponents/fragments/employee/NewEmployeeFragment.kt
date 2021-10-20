package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.employee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewEmployeeBinding
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentNewEmployeeBinding.inflate(inflater)
        // Inflate the layout for this fragment
        binding.neCancel.setOnClickListener{findNavController().navigate(
            R.id.action_newEmployeeFragment_to_employeeFragment)}

        binding.neSubmit.setOnClickListener{
            viewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
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

            val employee = Employee(_id = null, firstname = firstname, lastname = lastname, phone = phone,
                email = email, password = password, street = street, streetNumber = streetNumber,
                postArea = postArea, apartment = apartment, postCode = postCode,
                active = true,__v = 1, _vendor = null, createdAt = null,
                level = 1, updatedAt = null)

           viewModel.newEmployee(employee)

            findNavController().navigate(
                R.id.action_newEmployeeFragment_to_employeeFragment)

        }


        return binding.root


    }


}