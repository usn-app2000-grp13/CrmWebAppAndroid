package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentServiceBinding
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.models.employee.ServiceAdapter
import no.usn.gruppe4.crmwebappandroid.models.service.ServiceViewModel

class ServiceFragment : Fragment() {

    lateinit var binding : FragmentServiceBinding
    lateinit var serviceViewModel: ServiceViewModel
    private var serviceList = kotlin.collections.mutableListOf<Service>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentServiceBinding.inflate(inflater)

        // 1a) serviceViewModel
        serviceViewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)

        // 1b) Kaller serviceViewModel sin getServices()-metode. Den er et database kall
        serviceViewModel.getServices()

        // 2) ServiceAdapter (kobler sammen listen og recyclervievet)
        val adapter = ServiceAdapter(requireContext(), serviceList)
        binding.rvServices.adapter = adapter

        // 3) observable list i viewmodel (observerer endringer i services og oppdaterer serviceList)
        serviceViewModel.services.observe(viewLifecycleOwner, { service ->
            serviceList.clear()
            serviceList.addAll(service)
            adapter.notifyDataSetChanged()
            binding.rvServices.scheduleLayoutAnimation()
            Log.i("test", "services $serviceList")
        })

        // 4) Lytter metode som registrerer klikk på en service i listen
        adapter.setOnItemClickListener(object: ServiceAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putParcelable("service", serviceList[position])
                findNavController().navigate(R.id.action_serviceFragment_to_inspectServiceFragment, bundle)
            }
        })
        binding.rvServices.setHasFixedSize(true)

        // 5) Lytter metode som registrerer klikk på newService-knappen
        binding.fabNewService.setOnClickListener {
           findNavController().navigate(no.usn.gruppe4.crmwebappandroid.R.id.action_serviceFragment_to_newServiceFragment)
        }

        return binding.root
    }
}