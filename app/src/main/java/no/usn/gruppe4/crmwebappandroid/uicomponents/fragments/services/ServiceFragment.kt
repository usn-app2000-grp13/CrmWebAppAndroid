package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments.services

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentServiceBinding

class ServiceFragment : Fragment() {

    lateinit var binding : FragmentServiceBinding

    // 1) String tabell variabel
    lateinit var tab: Array<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentServiceBinding.inflate(inflater)

        // 2) initierer string tabell
        tab = resources.getStringArray(R.array.services)

        // 3) arrayAdapter (Denne bruker vi til å koble tabellen til listviewet)
        var arrayAdapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1,
                tab
            )
        }

        // 4) kobbler arrayAdapteren til listviewet i xml filen
        binding.listView.adapter = arrayAdapter

        // 5) Lytter metode som registrerer klikk i listen
        binding.listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            test(tab[position])
        }

        // 6) Lytter metode som registrerer klikk på newService-knappen
        binding.fabNewService.setOnClickListener {
           findNavController().navigate(R.id.action_serviceFragment_to_newServiceFragment)
        }

        return binding.root
    }

    private fun test(s: String) {
        //binding.textView.text = s
        findNavController().navigate(R.id.action_serviceFragment_to_inspectServiceFragment)
    }

}