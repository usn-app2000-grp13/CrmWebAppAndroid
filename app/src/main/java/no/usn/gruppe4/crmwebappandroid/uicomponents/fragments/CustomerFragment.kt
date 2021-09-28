package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentCustomerBinding

class CustomerFragment : Fragment() {

    lateinit var binding : FragmentCustomerBinding;
    lateinit var tab: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCustomerBinding.inflate(inflater)
        // Inflate the layout for this fragment

        // Fetch customer names from the strings.xml values.
        tab = resources.getStringArray(R.array.customers);
        // Tie the adapter to the named array
        var customerArrayAdapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                tab
            )
        }

        // link the adapter -> adapter.
        binding.customerList.adapter = customerArrayAdapter;

        // Add event on click for the customerList (list view)
        binding.customerList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            //test(tab[position])'
            System.out.println(tab[position]);
        }

        // Add event on click for the + symbol on the navigation button (lower right)
        binding.fabNewCustomer.setOnClickListener{
            findNavController().navigate(R.id.action_customerFragment_to_newCustomerFragment2);
        }

        return binding.root
    }

}