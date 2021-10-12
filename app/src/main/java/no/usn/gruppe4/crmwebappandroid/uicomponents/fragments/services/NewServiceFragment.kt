package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewServiceBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentServiceBinding
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.uicomponents.ServiceViewModel

class NewServiceFragment : Fragment() {

    lateinit var binding : FragmentNewServiceBinding
    lateinit var serviceViewModel: ServiceViewModel
    private var serviceList = kotlin.collections.mutableListOf<Service>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewServiceBinding.inflate(inflater)

        // 1a) serviceViewModel
        serviceViewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)

        // 1b) Kaller newService() serviceViewModel. Den er et database kall
        binding.btnSubmit.setOnClickListener{
            val description: String = binding.editAppTxtDescription.text.toString()
            val duration: Int = binding.editAppTxtDuration.text.toString().toInt()
            val name: String = binding.editAppTxtTitle.text.toString()
            val price: String =  binding.editAppTxtPrice.text.toString()
            val service = Service("null",description,duration,name,price)
            serviceViewModel.newService(service)
            findNavController().popBackStack()
        }

        binding.btnCancel.setOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }

}