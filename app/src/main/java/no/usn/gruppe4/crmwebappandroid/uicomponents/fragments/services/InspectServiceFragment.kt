package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.*
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentInspectServiceBinding
import no.usn.gruppe4.crmwebappandroid.models.IdRequest
import no.usn.gruppe4.crmwebappandroid.models.employee.EmployeeViewModel
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import no.usn.gruppe4.crmwebappandroid.models.service.ServiceViewModel

class InspectServiceFragment : Fragment() {

    lateinit var binding : FragmentInspectServiceBinding
    lateinit var service : Service
    lateinit var serviceViewModel: ServiceViewModel
    private var serviceList = kotlin.collections.mutableListOf<Service>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Service>("service").let { el->
            service = el!!
        }
        // 2a) serviceViewModel (db)
        serviceViewModel = ViewModelProvider(this).get(ServiceViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInspectServiceBinding.inflate(inflater)
        binding.tvTitleValue.text       = service.name
        binding.tvDescriptionValue.text = service.description
        binding.tvDurationValue.text    = service.duration.toString()
        binding.tvPriceValue.text       = service.price

        // Lytter metode som registrerer klikk på btnEdit-knappen
        binding.btnEditAppEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("service", service)
            findNavController().navigate(no.usn.gruppe4.crmwebappandroid.R.id.action_inspectServiceFragment_to_editServiceFragment,bundle)
        }

        // Lytter metode som registrerer klikk på btnDelete-knappen
        binding.btnEditAppDelete.setOnClickListener {

            val id = service._id
            serviceViewModel.removeService(IdRequest(id))

            /*val builder = AlertDialog.Builder(requireContext())
            builder.setMessage("Are you sure you want to Delete this Service?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
            //Knappene skal ha svart skrift
            val buttonPositive: Button = alert.getButton(DialogInterface.BUTTON_POSITIVE)
            buttonPositive.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            val buttonNegative: Button = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
            buttonNegative.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))*/
        }

        return binding.root
    }

}