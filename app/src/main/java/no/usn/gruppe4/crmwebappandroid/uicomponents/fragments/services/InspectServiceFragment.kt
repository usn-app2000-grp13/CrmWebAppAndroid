package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import ServiceAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentInspectServiceBinding
import no.usn.tj233512.myapplication.serviceAtribute

class InspectServiceFragment : Fragment() {

    lateinit var binding : FragmentInspectServiceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInspectServiceBinding.inflate(inflater)

        var serviceAtributes = mutableListOf(
            serviceAtribute("Title","Haircut Adult"),
            serviceAtribute("Description","Standard Haircut for adults"),
            serviceAtribute("Duration","45 min"),
            serviceAtribute("Price","500 kr"),
        )

        val adapter = ServiceAdapter(serviceAtributes) 	// lager en TodoAdapter. Sender med listen som parameter
        binding.rvServices.adapter = adapter	// kobler adapteren til recyclerViewen rvTodos
        binding.rvServices.layoutManager = LinearLayoutManager(activity) // velger layoutManager til recyclerViewen rvTodos

        return binding.root
    }

}