package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_settings.*
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentSettingsBinding
import no.usn.gruppe4.crmwebappandroid.models.Tools
import no.usn.gruppe4.crmwebappandroid.models.employee.Employee
import no.usn.gruppe4.crmwebappandroid.models.login.SecSharePref
import no.usn.gruppe4.crmwebappandroid.models.login.SharedPrefInterface
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginActivity
import no.usn.gruppe4.crmwebappandroid.uicomponents.SettingsViewModel


class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding
    private var editable = false
    private lateinit var sharedPreferences: SharedPrefInterface
    private lateinit var viewModel: SettingsViewModel
    private lateinit var user: Employee
    private val tools = Tools()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentSettingsBinding.inflate(inflater)

        //make link the secure shared preference file
        sharedPreferences = SecSharePref(requireContext(), "secrets")

        //set the viewModel
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        viewModel.getMyData(sharedPreferences.get("id"))

        viewModel.user.observe(viewLifecycleOwner, {
            user = it
            updateFields()
        })

        viewModel.status.observe(viewLifecycleOwner, {
            if (it == 3){
                viewModel.getMyData(sharedPreferences.get("id"))
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            if (it != null){
                Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnLogout.setOnClickListener {
            sharedPreferences.clear()
            viewModel.logout()
            val i = Intent(requireContext(), LoginActivity::class.java)
            startActivity(i)
        }

        binding.btnEditMode.setOnClickListener {
            turnEditable()
            editable = !editable
            if (editable){
                btnEditMode.text = getString(R.string.btnSubmit)
            }else{
                btnEditMode.text = getString(R.string.btnEdit)
                verifyData()
            }
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun updateFields() {
        binding.txtFname.setText(user.firstname)
        binding.txtLName.setText(user.lastname)
        binding.txtEmail.setText(user.email)
        binding.txtTlf.setText(user.phone)
        binding.txtAdr.setText(user.address?.street)
        binding.txtStreetNr.setText(user.address?.streetNumber)
        binding.txtPostArea.setText(user.address?.postArea)
        binding.txtPostCode.setText(user.address?.postCode)
        binding.txtApartment.setText(user.address?.apartment)

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
    }

    //make textField editable!
    private fun flipEditable(element: TextInputEditText, elmt2: TextInputLayout){
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
    private fun verifyData(){
        var address = user.address
        if (tools.verifyCheck(binding.txtFname)){ user.firstname = binding.txtFname.text.toString()}
        if (tools.verifyCheck(binding.txtLName)){ user. lastname = binding.txtLName.text.toString()}
        if (tools.verifyCheck(binding.txtEmail)){ user.email = binding.txtEmail.text.toString()}
        if (tools.verifyCheck(binding.txtTlf)){ user.phone = binding.txtTlf.text.toString()}
        if (tools.verifyCheck(binding.txtAdr)){ address?.street = binding.txtAdr.text.toString()}
        if (tools.verifyCheck(binding.txtStreetNr)){ address?.streetNumber = binding.txtStreetNr.text.toString()}
        if (tools.verifyCheck(binding.txtPostArea)){ address?.postArea = binding.txtPostArea.text.toString()}
        if (tools.verifyCheck(binding.txtPostCode)){ address?.postCode = binding.txtPostCode.text.toString()}
        if (binding.txtApartment.text != null) { address?.apartment = binding.txtApartment.text.toString()}
        viewModel.alterEmployee(user)
    }

}