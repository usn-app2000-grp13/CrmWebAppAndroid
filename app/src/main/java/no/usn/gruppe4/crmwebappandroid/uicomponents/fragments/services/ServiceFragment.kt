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

    // 1) String tabell variabel
    lateinit var tab: Array<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentServiceBinding.inflate(inflater)

        val serviceList:List<Service> = listOf(
            Service("Haircut Adult","Standard Haircut for adults","30 min","400 kr"),
            Service("Haircut Kids","Standard Haircut for kids","25 min","300 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
            Service("Haircut Teens","Standard Haircut for teens","30 min","400 kr"),
        )
        //val myDataset = services
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

        // 6) Lytter metode som registrerer klikk på newService-knappen
        binding.fabNewService.setOnClickListener {
           findNavController().navigate(no.usn.gruppe4.crmwebappandroid.R.id.action_serviceFragment_to_newServiceFragment)
        }
        return binding.root
    }
}