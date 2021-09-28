package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_settings.*
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentSettingsBinding
import no.usn.gruppe4.crmwebappandroid.models.appointment.Datasource
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginActivity
import no.usn.gruppe4.crmwebappandroid.uicomponents.MainActivity
import java.util.*


class SettingsFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    lateinit var binding: FragmentSettingsBinding
    var editable = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        binding = FragmentSettingsBinding.inflate(inflater)

        binding.btnLogout.setOnClickListener {
            val i = Intent(requireContext(), LoginActivity::class.java)
            startActivity(i)
        }
        binding.tfBirthDate.setEndIconOnClickListener {
            val cal = Datasource().getTodayCalender()
            val year: Int? = cal?.get(Calendar.YEAR)
            val month: Int? = cal?.get(Calendar.MONTH)
            val day: Int? = cal?.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(), this, year!!, month!!, day!!).show()
        }

        binding.btnEditMode.setOnClickListener {
            turnEditable()
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

    private fun turnEditable(){
        flipEditable(binding.txtFname, binding.tfFirstName)
        flipEditable(binding.txtLName, binding.tfLastName)
        flipEditable(binding.txtEmail, binding.tfEmail)
        flipEditable(binding.txtTlf, binding.tfTlf)
        flipEditable(binding.txtAdr, binding.tfAdr)
        flipEditable(binding.txtStreetNr, binding.tfStreetNr)
        flipEditable(binding.txtPostArea, binding.tfPostArea)
        flipEditable(binding.txtPostCode, binding.tfPostCode)
        flipEditable(binding.txtApartment, binding.tfApartment)
        flipEditable(binding.txtBirthDate, binding.tfBirthDate)
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

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val day = reformatDateTime(p3)
        val month = reformatDateTime(p2)
        val year = reformatDateTime(p1)
        binding.txtBirthDate.setText("$day/$month/$year")
    }

    fun reformatDateTime(element: Int): String{
        var res = ""
        if (element < 10){
            res += "0$element"
        }else res += element.toString()
        return res
    }


}