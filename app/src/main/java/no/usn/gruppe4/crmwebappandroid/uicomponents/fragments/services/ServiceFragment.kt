package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentServiceBinding
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import ServiceAdapter

class ServiceFragment : Fragment() {

    lateinit var binding : FragmentServiceBinding

    // 1) String tabell variabel
    lateinit var tab: Array<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentServiceBinding.inflate(inflater)

        var servicees = mutableListOf(
            Service("Haircut Adult","Standard Haircut for adults","30 min","400 kr"),
            Service("Haircut Kids","Standard Haircut for kids","25 min","300 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr")
        )

        val adapter = ServiceAdapter(servicees) 	// lager en TodoAdapter. Sender med listen som parameter
        binding.rvServices.adapter = adapter	// kobler adapteren til recyclerViewen rvTodos
        binding.rvServices.layoutManager = LinearLayoutManager(activity) // velger layoutManager til recyclerViewen rvTodos

        // 5) Lytter metode som registrerer klikk i listen !!!!!!!!
        binding.textView.setOnClickListener {
            findNavController().navigate(no.usn.gruppe4.crmwebappandroid.R.id.action_serviceFragment_to_inspectServiceFragment)
        }

        // 6) Lytter metode som registrerer klikk på newService-knappen
        binding.fabNewService.setOnClickListener {
           findNavController().navigate(no.usn.gruppe4.crmwebappandroid.R.id.action_serviceFragment_to_newServiceFragment)
        }

        return binding.root
    }

    private fun test(s: String) {
        //binding.textView.text = s
        findNavController().navigate(no.usn.gruppe4.crmwebappandroid.R.id.action_serviceFragment_to_inspectServiceFragment)
    }

}