package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_settings.*
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentSettingsBinding
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginActivity
import no.usn.gruppe4.crmwebappandroid.uicomponents.MainActivity


class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    var editable = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentSettingsBinding.inflate(inflater)

        binding.btnLogout.setOnClickListener {
            val i = Intent(requireContext(), LoginActivity::class.java)
            startActivity(i)
        }
        binding.btnEditMode.setOnClickListener {
            flipEditable(binding.txtFname, binding.tfFirstName)
            flipEditable(binding.txtLName, binding.tfLastName)
            flipEditable(binding.txtEmail, binding.tfEmail)
            editable = !editable
            if (editable){
                btnEditMode.text = "Done"
            }else{
                btnEditMode.text = "Edit"
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    //make textField editable!
    fun flipEditable(element: TextInputEditText, elmt2: TextInputLayout){
        if (!editable){
            element.isClickable = true
            element.isCursorVisible = true
            element.isFocusable = true
            element.isFocusableInTouchMode = true
            elmt2.setBoxBackgroundColorResource(R.color.grey)
        }else{
            element.isClickable = false
            element.isCursorVisible = false
            element.isFocusable = false
            element.isFocusableInTouchMode = false
            elmt2.setBoxBackgroundColorResource(R.color.white)
        }

    }


}