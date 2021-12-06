package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.employee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_employee_card.*
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEmployeeCardBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewEmployeeBinding
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeViewModel
import no.usn.gruppe4.crmwebappandroid.models.login.SessionResponse


class EmployeeCard : Fragment() {

    lateinit var binding: FragmentEmployeeCardBinding
    lateinit var viewModel: EmployeeViewModel

    private var employee: Employee? = null
    private var login : SessionResponse.Data? = null
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
            findNavController().popBackStack()
        }



        var tvEmpFirstnameValue = binding.tvEmpFirstnameValue
        var tvEmpLastnameValue = binding.tvEmpLastnameValue
        var tvEmpPhoneValue = binding.tvEmpPhoneValue
        var tvEmpEmailValue = binding.tvEmpEmailValue
        var tvEmpLevelValue = binding.tvEmpLevelValue
        var tvEmpAddressValue = binding.tvEmpAddressValue

        viewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        employee?._id?.let { viewModel.getEmployee(it) }
        viewModel.employee.observe(viewLifecycleOwner,{
            employee = it


        tvEmpFirstnameValue.text = employee?.firstname
        tvEmpLastnameValue.text = employee?.lastname
        tvEmpPhoneValue.text = employee?.phone
        tvEmpEmailValue.text = employee?.email
        tvEmpLevelValue.text = employee?.level.toString()
        tvEmpAddressValue.text = employee?.address?.street + " " + employee?.address?.streetNumber + ", " + employee?.address?.postArea + " " + employee?.address?.postCode
        })
        login = SessionResponse.Data(active = false,firstname = "", lastname = "", id = "", level = 0, vendor = "")
        login.let {viewModel.getLogin()}

        viewModel.login.observe(viewLifecycleOwner,{
            login = it


        if(login!!.level >= 2){
            binding.btnEmpEdit.visibility = View.VISIBLE
            binding.btnEmDelete.visibility = View.VISIBLE
            binding.btnEmpEdit.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("employee", employee)
                findNavController().navigate(R.id.action_employeeCard_to_editEmployeeFragment,bundle)
            }
            binding.btnEmDelete.setOnClickListener{
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Are you sure you want to DELETE this employee?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, _ ->
                        // Dismiss the dialog
                        val employee = EmployeeViewModel.DeleteEmployee(id = employee?._id)
                        viewModel.deleteEmployee(employee)
                        dialog.dismiss()
                        var test = false
                        viewModel.bool.observe(viewLifecycleOwner,{
                            test =it
                            if(test){
                                findNavController().popBackStack()
                            }else{
                                val text = "Api has not accepted the request yet!"
                                val duration = Toast.LENGTH_SHORT


                                val toast = Toast.makeText(context, text, duration)
                                toast.show()

                            }
                        })
                        if(!test){
                            val text = "Api error!"
                            val duration = Toast.LENGTH_SHORT


                            val toast = Toast.makeText(context, text, duration)
                            toast.show()
                        }
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        } })




        return binding.root
    }

}