package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewServiceBinding
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.models.service.ServiceViewModel

class NewServiceFragment : Fragment() {

    lateinit var binding : FragmentNewServiceBinding
    lateinit var serviceViewModel: ServiceViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewServiceBinding.inflate(inflater)

        // 1a) serviceViewModel
        serviceViewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)
        serviceViewModel.newAction()

        // 1b) Kaller newService() serviceViewModel. Den er et database kall
        binding.btnSubmit.setOnClickListener{
            var ok = true
            if (!isFilled(binding.editAppTxtTitle)) ok = false;
            if (!isFilled(binding.editAppTxtDescription)) ok = false;
            if (!isFilled(binding.editAppTxtDuration)) ok = false;
            if (!isFilled(binding.editAppTxtPrice)) ok = false;

            if (ok) {
                var name = binding.editAppTxtTitle.text.toString()
                var description = binding.editAppTxtDescription.text.toString()
                var duration = binding.editAppTxtDuration.text.toString()
                var price =  binding.editAppTxtPrice.text.toString()
                var service = Service("null",description,duration.toInt(),name,price)
                serviceViewModel.newService(service)
            }
        }

        binding.btnCancel.setOnClickListener{
            findNavController().popBackStack()
        }

        serviceViewModel.adding.observe(viewLifecycleOwner,{
            if (it) {
                findNavController().popBackStack()
            }
        })

        return binding.root
    }

    fun isFilled(element: TextInputEditText): Boolean{
        if (element.length() < 1){
            element.error = "Required!"
            return false
        }else{
            return true
        }
    }

}

