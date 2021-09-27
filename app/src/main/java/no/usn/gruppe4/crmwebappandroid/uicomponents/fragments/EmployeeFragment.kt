package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEmployeeBinding


class EmployeeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentEmployeeBinding.inflate(inflater)
        // Inflate the layout for this fragment
        binding.fabNewEmployee.setOnClickListener {
            findNavController().navigate(R.id.action_employeeFragment_to_newEmployeeFragment2)
        }
        return binding.root
    }

}