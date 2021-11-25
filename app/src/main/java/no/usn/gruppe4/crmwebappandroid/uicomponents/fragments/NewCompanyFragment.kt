package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_new_company.*
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentLoginBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewAppointmentBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewCompanyBinding
import no.usn.gruppe4.crmwebappandroid.models.employee.Address
import no.usn.gruppe4.crmwebappandroid.models.login.LoginRequest
import no.usn.gruppe4.crmwebappandroid.models.login.SecSharePref
import no.usn.gruppe4.crmwebappandroid.models.login.SharedPrefInterface
import no.usn.gruppe4.crmwebappandroid.uicomponents.CalanderViewModel
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginViewModel
import no.usn.gruppe4.crmwebappandroid.uicomponents.MainActivity

private const val TAG = "NewCompanyFragment"
class NewCompanyFragment : Fragment() {

    private lateinit var binding: FragmentNewCompanyBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreferences: SharedPrefInterface
    private lateinit var company: newCompany

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //inflate the layout and bind the binding
        binding = FragmentNewCompanyBinding.inflate(inflater)

        //add viewModel
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        //make link the secure shared preference file
        sharedPreferences = SecSharePref(requireContext(), "secrets")

        //button submit listener
        binding.neSubmit.setOnClickListener {
            verifyData()
        }
        //button cancel listener
        binding.neCancel.setOnClickListener {
            findNavController().navigate(R.id.action_newCompanyFragment_to_loginFragment)
        }

        //password confirm check
        binding.txtNeCompanyConfirmPass.doOnTextChanged { text, start, before, count ->
            if (binding.txtNeCompanyPassword.text.contentEquals(binding.txtNeCompanyConfirmPass.text)){
                binding.txtNeCompanyConfirmPass.error = null
            }else{
                binding.txtNeCompanyConfirmPass.error = "Passwords do not match!"
            }
        }

        //viewmodel observers
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            if (it == null){
                Log.i("ErrorMessageTest", "No errors")
            } else {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        //Api call loading
        viewModel.isLoading.observe(viewLifecycleOwner, {
            if (it == false){
                binding.newCompanyProgressBar.visibility = View.GONE
            }else {
                binding.newCompanyProgressBar.visibility = View.VISIBLE
            }
        })

        viewModel.session.observe(viewLifecycleOwner, {
            sharedPreferences.put("name", "${viewModel.session.value!!.firstname} ${viewModel.session.value!!.lastname}")
            sharedPreferences.put("id", viewModel.session.value!!.id)
            sharedPreferences.putBoolean("logged", true)
            sharedPreferences.put("level", viewModel.session.value!!.level.toString())
            val i = Intent(activity, MainActivity::class.java)
            startActivity(i)
        })

        viewModel.status.observe(viewLifecycleOwner, {
            if (it == 6){
                viewModel.loginCall(LoginRequest(company.password!!, company.email!!))
            }
        })

        return binding.root
        }

    fun verifyCheck(element: TextInputEditText): Boolean{
        if (element.length() < 1){
            element.error = "Required!"
            return false
        }else{
            return true
        }
    }
    private fun verifyData(){
        company = newCompany(null,null,null,null,null,null,null,null,null)
        val address = Address(null, null, null, null, null)
        //company name
        if (verifyCheck(binding.txtNeCompanyName)){ company.vendorName = binding.txtNeCompanyName.text.toString() }
        //company phone
        if (verifyCheck(binding.txtNePhone)){ company.vendorPhone = binding.txtNePhone.text.toString() }
        //company email
        if (verifyCheck(binding.txtNeEmail)){ company.vendorEmail = binding.txtNeEmail.text.toString() }
        //company street
        if (verifyCheck(binding.txtNeCompanyStreet)){ address.street = binding.txtNeCompanyStreet.text.toString() }
        //company streetnr
        if (verifyCheck(binding.txtNeCompanyStreetNr)){ address.streetNumber = binding.txtNeCompanyStreetNr.text.toString() }
        //company appartment
        if (verifyCheck(binding.txtNeCompanyApartment)){ address.apartment = binding.txtNeCompanyApartment.text.toString() }
        //company post area
        if (verifyCheck(binding.txtNeCompanyPostArea)){ address.postArea = binding.txtNeCompanyPostArea.text.toString() }
        //company post code
        company.vendorAddress = address
        if (verifyCheck(binding.txtNeCompanyNamePostCode)){ address.postCode = binding.txtNeCompanyNamePostCode.text.toString() }
        //owner first name
        if (verifyCheck(binding.txtNeCompanyFirstName)){ company.firstname = binding.txtNeCompanyFirstName.text.toString() }
        //owner last name
        if (verifyCheck(binding.txtNeCompanyLastName)){ company.lastname = binding.txtNeCompanyLastName.text.toString() }
        //owner email
        if (verifyCheck(binding.txtNeCompanyUserEmail)){ company.email = binding.txtNeCompanyUserEmail.text.toString() }
        //owner phonenumber
        if (verifyCheck(binding.txtNeCompanyUserPhone)){ company.phone = binding.txtNeCompanyUserPhone.text.toString() }
        //password
        if (binding.txtNeCompanyPassword.text.contentEquals(binding.txtNeCompanyConfirmPass.text)){
            if (verifyCheck(binding.txtNeCompanyConfirmPass)){ company.password = binding.txtNeCompanyConfirmPass.text.toString() }
        }
        if (company.checkFields()){
            //API CALL
            Log.i(TAG, "result = $company")
            viewModel.newCompany(company)
        }
    }


    data class newCompany(
        var firstname: String?,
        var lastname: String?,
        var email: String?,
        var phone: String?,
        var password: String?,
        var vendorName: String?,
        var vendorEmail: String?,
        var vendorPhone: String?,
        var vendorAddress: Address?,
    ){
        fun checkFields(): Boolean{
            return firstname != null &&
                    lastname != null &&
                    email != null &&
                    phone != null &&
                    password != null &&
                    vendorName != null &&
                    vendorEmail != null &&
                    vendorPhone != null &&
                    vendorAddress != null
        }
    }

}