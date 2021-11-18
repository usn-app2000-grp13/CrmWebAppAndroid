package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentCustomerBinding
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.customer.CustomerAdapter
import no.usn.gruppe4.crmwebappandroid.models.customer.CustomerViewModel

class CustomerFragment : Fragment() {
    //en egen liste i customerFragment som vi kan sende inn til adapteren (pga. grafisk crash)
    private val customerList = mutableListOf<Customer>()

    //legger disse utenfor så at du kan bruke dem i funksjoner
    lateinit var viewModel: CustomerViewModel
    lateinit var binding: FragmentCustomerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCustomerBinding.inflate(inflater)
        //la till viewmodelen for customers for å gjøre DB call og observeList
        val viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        //kaller på metode i viewmodel (pga. async muligheter)
        viewModel.getCustomers()
        val adapter = CustomerAdapter(requireContext(), customerList)

        //for hver endring i originale listen så endrer customerList seg og sier vi ifra om oppdatering til adapteren
        viewModel.customers.observe(viewLifecycleOwner, {
            customerList.clear()
            customerList.addAll(it)
            adapter.notifyDataSetChanged()
        })


        binding.customerRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object: CustomerAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                println(customerList[position])
                val bundle = Bundle()
                bundle.putParcelable("customer", customerList[position])
                findNavController().navigate(R.id.action_customerFragment_to_customerEditFragment, bundle)

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