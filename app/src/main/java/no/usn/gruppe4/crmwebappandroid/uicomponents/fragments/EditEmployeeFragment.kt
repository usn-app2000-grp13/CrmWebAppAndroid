package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEditEmployeeBinding
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee


class EditEmployeeFragment : Fragment() {
    private var employee: Employee? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Employee>("employee").let { el->
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
        binding.txtNeStreet.setText(employee?.street)
        binding.txtNeStreetNumber.setText(employee?.streetNumber)
        binding.txtNePostArea.setText(employee?.postArea)
        binding.txtNeApartment.setText(employee?.apartment)
        binding.txtNePostCode.setText(employee?.postCode)

        binding.neCancel.setOnClickListener{
            val bundle = Bundle()
            bundle.putParcelable("employee", employee)
            findNavController().navigate(
            R.id.action_editEmployeeFragment_to_employeeCard,bundle
        )}




        return binding.root
    }


}