package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.employee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEditEmployeeBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewEmployeeBinding
import no.usn.gruppe4.crmwebappandroid.models.employee.Address
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeViewModel


class EditEmployeeFragment : Fragment() {
    private var employee: Employee? = null
    lateinit var binding: FragmentNewEmployeeBinding
    lateinit var viewModel: EmployeeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Employee>("employee").let { el ->
            employee = el
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditEmployeeBinding.inflate(inflater)
        binding.txtNeFirstName.setText(employee?.firstname)
        binding.txtNeLastName.setText(employee?.lastname)
        binding.txtNePhone.setText(employee?.phone)
        binding.txtNeEmail.setText(employee?.email)
        binding.txtNeStreet.setText(employee?.address?.street)
        binding.txtNeStreetNumber.setText(employee?.address?.streetNumber)
        binding.txtNePostArea.setText(employee?.address?.postArea)
        binding.txtNeApartment.setText(employee?.address?.apartment)
        binding.txtNePostCode.setText(employee?.address?.postCode)

        binding.neCancel.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("employee", employee)
            findNavController().navigate(
                R.id.action_editEmployeeFragment_to_employeeCard, bundle
            )
        }
        binding.neSubmit.setOnClickListener {
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

            val newAddress = Address(
                postArea = postArea, apartment = apartment,
                postCode = postCode, street = street, streetNumber = streetNumber,
            )

            val newEmployee = Employee(
                _id = employee?._id, firstname = firstname, lastname = lastname,
                phone = phone,
                email = email, password = password, address = newAddress, active = true,
                __v = employee?.__v, _vendor = employee?._vendor, createdAt = employee?.createdAt,
                level = employee?.level, updatedAt = null
            )
            viewModel.alterEmployee(newEmployee)
            val bundle = Bundle()
            bundle.putParcelable("employee", newEmployee)
            findNavController().navigate(
                R.id.action_editEmployeeFragment_to_employeeCard, bundle
            )
        }




        return binding.root
    }


}