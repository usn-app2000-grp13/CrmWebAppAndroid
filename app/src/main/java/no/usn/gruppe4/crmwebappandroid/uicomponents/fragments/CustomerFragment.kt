package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentCustomerBinding
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.customer.CustomerAdapter

class CustomerFragment : Fragment() {

    private var customerList = getCustomerList()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCustomerBinding.inflate(inflater)
        val adapter = CustomerAdapter(requireContext(), customerList)
        binding.customerRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object: CustomerAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                println(customerList[position])
                val bundle = Bundle()
                bundle.putParcelable("customer", customerList[position])
                //findNavController().navigate(R.id.action_employeeFragment_to_employeeCard, bundle)
            }
        })

        // Make sure the List has a fixed size
        binding.customerRecyclerView.setHasFixedSize(true)

        // Add event on click for the + symbol on the navigation button (lower right)
        binding.fabNewCustomer.setOnClickListener{
            findNavController().navigate(R.id.action_customerFragment_to_newCustomerFragment2)
        }

        return binding.root
    }

    private fun getCustomerList(): List<Customer> {
        // Add DB check to .GET customers.
        var newCustomerList : List<Customer> = mutableListOf(Customer("123", "Geir", "Person", "GP@yahoo.se", "+46 422 152 "))
        /*
        *  Here we add the details from the DB...
        * val _id: String?,
        * val firstname: String?,
        * val lastname: String?,
        * val email: String?,
        * val phone: String?,
        */

        //newCustomerList.add("test", "Peter", "Jackson", "pete@jackson.com", "123123123")


        return newCustomerList
    }
}