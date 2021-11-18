package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

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
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentCustomerEditBinding
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.customer.CustomerViewModel
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeViewModel

/**
 *
 */
class CustomerEditFragment : Fragment() {
    lateinit var binding: FragmentCustomerEditBinding
    lateinit var viewModel: CustomerViewModel

    // Init customer as null
    private var customer: Customer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Customer>("customer").let { el->
            customer = el
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentCustomerEditBinding.inflate(inflater)
        val viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
            findNavController().navigate(R.id.action_customerEditFragment_to_customerFragment)
        }

        var custFirstName = binding.editAppTxtCustomerFirstname
        var custLastName = binding.editAppTxtCustomerLastname
        var custPhone = binding.editAppTxtCustomerPhone
        var custEmail = binding.editAppTxtCustomerEmail

        custFirstName.setText(customer?.firstname)
        custLastName.setText(customer?.lastname)
        custPhone.setText(customer?.phone)
        custEmail.setText(customer?.email)


        binding.customerDelete.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure you want to Delete ${customer?.firstname}?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    val deleteCustomer = CustomerViewModel.DeleteCustomer(id = customer?._id)
                    viewModel.deleteCustomer(deleteCustomer)
                    findNavController().navigate(R.id.action_customerEditFragment_to_customerFragment)
                }
                .setNegativeButton("No") { dialog, _ ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        binding.customerSubmit.setOnClickListener {
            /* Handle verification to submit */
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure you want to save the changes?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    val newFirstName = binding.editAppTxtCustomerFirstname?.text?.toString()
                    val newLastName = binding.editAppTxtCustomerLastname?.text?.toString()
                    val newPhone = binding.editAppTxtCustomerPhone?.text?.toString()
                    val newEmail = binding.editAppTxtCustomerEmail?.text?.toString()

                    var newCustomer = Customer(
                        _id = customer?._id, firstname = newFirstName, lastname = newLastName, phone = newPhone, email = newEmail
                    )

                    viewModel.updateCustomer(newCustomer)
                    // Dismiss the dialog
                    findNavController().navigate(R.id.action_customerEditFragment_to_customerFragment)
                }
                .setNegativeButton("No") { dialog, _ ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        binding.customerCancel.setOnClickListener {
                        findNavController().navigate(
                R.id.action_customerEditFragment_to_customerFragment
            )
        }
        // Return the binding
        return binding.root
    }
}