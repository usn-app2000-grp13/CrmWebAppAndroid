package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewCustomerBinding
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.customer.CustomerViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [NewCustomerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewCustomerFragment : Fragment() {
    lateinit var binding: FragmentNewCustomerBinding
    lateinit var viewModel:CustomerViewModel

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentNewCustomerBinding.inflate(inflater)
        // Inflate the layout for this fragment
        binding.customerCancel.setOnClickListener{findNavController().navigate(
            R.id.action_newCustomerFragment_to_customerFragment)}

        binding.customerSubmit.setOnClickListener{
            viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
            val firstname = binding.editAppTxtCustomerFirstname.text?.toString()
            val lastname = binding.editAppTxtCustomerLastname.text?.toString()
            val phone = binding.editAppTxtCustomerPhone.text?.toString()
            val email = binding.editAppTxtCustomerEmail.text?.toString()

            val customer = Customer(_id = null, firstname = firstname, lastname = lastname, phone = phone,
                email = email)

            viewModel.newCustomer(customer)

            findNavController().navigate(
                R.id.action_newCustomerFragment_to_customerFragment)
        }


        return binding.root


    }


}