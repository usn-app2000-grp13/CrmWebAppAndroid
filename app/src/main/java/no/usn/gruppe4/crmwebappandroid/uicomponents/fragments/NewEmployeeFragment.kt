package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewEmployeeBinding


/**
 * A simple [Fragment] subclass.
 * Use the [NewEmployeeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewEmployeeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentNewEmployeeBinding.inflate(inflater)
        // Inflate the layout for this fragment
        binding.neCancel.setOnClickListener{findNavController().navigate(
            R.id.action_newEmployeeFragment_to_employeeFragment)}
        return binding.root
    }


}