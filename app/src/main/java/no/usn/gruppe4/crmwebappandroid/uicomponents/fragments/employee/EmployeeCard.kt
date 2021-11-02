package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.employee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEmployeeCardBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewEmployeeBinding
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeViewModel


class EmployeeCard : Fragment() {

    lateinit var binding: FragmentNewEmployeeBinding
    lateinit var viewModel: EmployeeViewModel

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
        // This callback will only be called when MyFragment is at least Started.
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            findNavController().navigate(R.id.action_employeeCard_to_employeeFragment)
        }

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
        tvEmpLevelValue.text = employee?.level.toString()
        tvEmpAddressValue.text = employee?.address?.street + " " + employee?.address?.streetNumber + ", " + employee?.address?.postArea + " " + employee?.address?.postCode

        binding.btnEmpEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("employee", employee)
            findNavController().navigate(R.id.action_employeeCard_to_editEmployeeFragment,bundle)
        }
        binding.btnEmDelete.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure you want to DELETE this employee?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Dismiss the dialog
                    viewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
                    val employee = EmployeeViewModel.DeleteEmployee(id = employee?._id)
                    viewModel.deleteEmployee(employee)
                    dialog.dismiss()
                    findNavController().navigate(
                        R.id.action_employeeCard_to_employeeFragment)
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }


        return binding.root
    }

}