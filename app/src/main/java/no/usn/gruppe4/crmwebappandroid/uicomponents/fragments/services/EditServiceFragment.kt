package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.app.AlertDialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEditServiceBinding
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.models.service.ServiceViewModel

class EditServiceFragment : Fragment() {

    lateinit var binding : FragmentEditServiceBinding
    lateinit var service : Service
    lateinit var serviceViewModel: ServiceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1a) Den servicen som ble klikket på blir sendt som en parcable og lagret her
        arguments?.getParcelable<Service>("service").let { el->
            service = el!!
        }

        // 1b) serviceViewModel (db)
        serviceViewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 2a) Setter den aktuelle servicen sine verdier inn
        binding = FragmentEditServiceBinding.inflate(inflater)
        binding.editAppTxtTitle.setText(service.name)
        binding.editAppTxtDescription.setText(service.description)
        binding.editAppTxtDuration.setText(service.duration.toString())
        binding.editAppTxtPrice.setText(service.price)

        // 2b) Update knappen kaller serviceViewModel.updateService(service) (db)
        binding.btnApply.setOnClickListener{
            var ok = true
            if (!isFilled(binding.editAppTxtTitle)) ok = false;
            if (!isFilled(binding.editAppTxtDescription)) ok = false;
            if (!isFilled(binding.editAppTxtDuration)) ok = false;
            if (!isFilled(binding.editAppTxtPrice)) ok = false;
            if (ok) {
                service.name = binding.editAppTxtTitle.text.toString()
                service.description = binding.editAppTxtDescription.text.toString()
                service.duration = binding.editAppTxtDuration.text.toString().toInt()
                service.price =  binding.editAppTxtPrice.text.toString()
                serviceViewModel.updateService(service)
                findNavController().popBackStack()
            }
        }

        // 2c)Cancel knappen sender brukeren tilbake
        binding.btnCancel.setOnClickListener{
            findNavController().popBackStack()
        }

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