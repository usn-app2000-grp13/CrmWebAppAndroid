package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.test.platform.app.InstrumentationRegistry
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentInspectServiceBinding
import no.usn.gruppe4.crmwebappandroid.models.appointment.Appointment
import no.usn.gruppe4.crmwebappandroid.models.service.Service
import android.widget.TextView
import androidx.core.content.PackageManagerCompat.LOG_TAG

import android.content.DialogInterface

import android.text.Html
import android.util.Log
import androidx.core.content.PackageManagerCompat
import android.R
import android.graphics.Color.green
import android.widget.Button

import androidx.core.content.ContextCompat

class InspectServiceFragment : Fragment() {

    lateinit var binding : FragmentInspectServiceBinding
    lateinit var service : Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Service>("service").let { el->
            service = el!!
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInspectServiceBinding.inflate(inflater)
        binding.tvTitleValue.text       = service.title
        binding.tvDescriptionValue.text = service.description
        binding.tvDurationValue.text    = service.duration
        binding.tvPriceValue.text       = service.price

        // Lytter metode som registrerer klikk på btnEdit-knappen
        binding.btnEditAppEdit.setOnClickListener {
            val bundle = Bundle()
            bundle.putParcelable("service", service)
            findNavController().navigate(no.usn.gruppe4.crmwebappandroid.R.id.action_inspectServiceFragment_to_editServiceFragment,bundle)
        }

        // Lytter metode som registrerer klikk på btnDelete-knappen
        binding.btnEditAppDelete.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
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
            buttonNegative.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        return binding.root
    }

}