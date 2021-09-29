package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_employee_card.*
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEmployeeCardBinding
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee


class EmployeeCard : Fragment() {

    private var employee: Employee? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Employee>("employee").let { el->
            employee = el
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentEmployeeCardBinding.inflate(inflater)
        // Inflate the layout for this fragment
        var tvEmpFirstnameValue = binding.tvEmpFirstnameValue
        var tvEmpLastnameValue = binding.tvEmpLastnameValue
        var tvEmpPhoneValue = binding.tvEmpPhoneValue
        var tvEmpEmailValue = binding.tvEmpEmailValue
        var tvEmpLevelValue = binding.tvEmpLevelValue
        var tvEmpAddressValue = binding.tvEmpAddressValue

        tvEmpFirstnameValue.text = employee?.firstname
        tvEmpLastnameValue.text = employee?.lastname
        tvEmpPhoneValue.text = employee?.phone
        tvEmpEmailValue.text = employee?.email
        tvEmpLevelValue.text = employee?.level
        tvEmpAddressValue.text = employee?.street + " " + employee?.streetNumber

        binding.btnEmpEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("employee", employee)
            findNavController().navigate(R.id.action_employeeCard_to_editEmployeeFragment,bundle)
        }


        return binding.root
    }

}