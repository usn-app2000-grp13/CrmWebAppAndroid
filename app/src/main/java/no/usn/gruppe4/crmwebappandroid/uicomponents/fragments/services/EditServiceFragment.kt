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

        // 2a) serviceViewModel (db)
        serviceViewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 1b) Setter den aktuelle servicen sine verdier inn
        binding = FragmentEditServiceBinding.inflate(inflater)
        binding.editAppTxtTitle.setText(service.name)
        binding.editAppTxtDescription.setText(service.description)
        binding.editAppTxtDuration.setText(service.duration.toString())
        binding.editAppTxtPrice.setText(service.price)

        // 2b) Update knappen kaller serviceViewModel.updateService(service) (db)
        binding.btnApply.setOnClickListener{
            var name = binding.editAppTxtTitle.text.toString()
            var description = binding.editAppTxtDescription.text.toString()
            var duration = binding.editAppTxtDuration.text.toString()
            var price =  binding.editAppTxtPrice.text.toString()

            if (name == "") {
                Toast.makeText(requireContext(), getString(R.string.missingNameMsg), Toast.LENGTH_LONG).show()
            }
            else if (description == "") {
                Toast.makeText(requireContext(), getString(R.string.missingDescriptionMsg), Toast.LENGTH_LONG).show()
            }
            else if (duration == "") {
                Toast.makeText(requireContext(), getString(R.string.missingDurationMsg), Toast.LENGTH_LONG).show()
            }
            else if (price == "") {
                Toast.makeText(requireContext(), getString(R.string.missingPriceMsg), Toast.LENGTH_LONG).show()
            }
            else {
                service.name = name
                service.description = description
                service.duration = duration.toInt()
                service.price =  price
                serviceViewModel.updateService(service)
                findNavController().popBackStack()
            }
        }

        // Cancel knappen sender brukeren tilbake
        binding.btnCancel.setOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }

}