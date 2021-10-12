package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentServiceBinding
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.models.appointment.AppointmentResponse
import no.usn.gruppe4.crmwebappandroid.models.employee.ServiceAdapter
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import no.usn.gruppe4.crmwebappandroid.uicomponents.ServiceViewModel

class ServiceFragment : Fragment() {

    lateinit var binding : FragmentServiceBinding
    lateinit var viewModel: ServiceViewModel
    private var serviceList = kotlin.collections.mutableListOf<Service>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentServiceBinding.inflate(inflater)
        // set viewmodel
        viewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)
        // database call
        viewModel.getServices()


/*
        val serviceList:List<Service> = listOf(
            Service("HairCut Adult","Standard hairCut for Adult","30 min","400 kr"),
            Service("Hair Coloring","Standard hair Coloring","40 min","500 kr"),
            Service("HairCut Kids","Standard hairCut for Kids","20 min","300 kr"),
            Service("Bride Package","Fancy bride Package","60 min","700 kr"),
            Service("Wash and styling","Standard hair Washing and styling","20 min","500 kr"),
            Service("Eyebrow Wax","Standard Eyebrow Wax","30 min","385 kr"),
            Service("Perm, short hair","Standard Perm for short hair","90 min","1150 kr"),
            Service("Perm, long hair","Standard Perm for long hair","120 min","1450 kr"),
        )
*/
        val adapter = ServiceAdapter(requireContext(), serviceList)
        binding.rvServices.adapter = adapter

        //observable list i viewmodel
        viewModel.services.observe(viewLifecycleOwner, { service ->
            serviceList.clear()
            serviceList.addAll(service)
            adapter.notifyDataSetChanged()
            Log.i("test", "services $serviceList")
        })

        adapter.setOnItemClickListener(object: ServiceAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putParcelable("service", serviceList[position])
                findNavController().navigate(R.id.action_serviceFragment_to_inspectServiceFragment, bundle)
            }
        })
        binding.rvServices.setHasFixedSize(true)



        // Lytter metode som registrerer klikk på newService-knappen
        binding.fabNewService.setOnClickListener {
           findNavController().navigate(no.usn.gruppe4.crmwebappandroid.R.id.action_serviceFragment_to_newServiceFragment)
        }

        return binding.root
    }
}