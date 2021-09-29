package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentServiceBinding
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.models.employee.ServiceAdapter

class ServiceFragment : Fragment() {

    lateinit var binding : FragmentServiceBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentServiceBinding.inflate(inflater)

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

        val adapter = ServiceAdapter(requireContext(), serviceList)
        binding.rvServices.adapter = adapter

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