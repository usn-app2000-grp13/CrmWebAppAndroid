package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentNewCustomerBinding
import no.usn.gruppe4.crmwebappandroid.models.customer.Customer
import no.usn.gruppe4.crmwebappandroid.models.customer.CustomerViewModel


class NewCustomerFragment : Fragment() {
    lateinit var binding: FragmentNewCustomerBinding
    lateinit var viewModel:CustomerViewModel

    private fun checkEmail(email: String): Boolean {
        /* https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/-regex/ */
        val emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        return email.matches(Regex(emailPattern))
    }

    private fun checkIfInputIsCorrect(input: String): Boolean {
        /* Check if it is not empty, null or blank. */
        if (input.isEmpty() || input.isBlank()){
            return false
        }
        return true
    }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentNewCustomerBinding.inflate(inflater)
        // Inflate the layout for this fragment
        binding.customerCancel.setOnClickListener{findNavController().navigate(
            R.id.action_newCustomerFragment_to_customerFragment)}

        binding.customerSubmit.setOnClickListener{
            Log.d("Clicked", "StartedCustomerBuild")
            viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
            val firstname = binding.editAppTxtCustomerFirstname.text?.toString()
            val lastname = binding.editAppTxtCustomerLastname.text?.toString()
            val phone = binding.editAppTxtCustomerPhone.text?.toString()
            val email = binding.editAppTxtCustomerEmail.text?.toString()

            var feedBackMessage = ""
            val validEmail = email?.let { it1 -> checkEmail(it1) }
            if (validEmail == true){
                Log.d("validate", "Email validated")
            } else {
                Log.d("validate", "Email NOT validated")
                feedBackMessage = "Email not valid"
                binding.editAppTxtCustomerEmail.error = "Email NOT validated"
            }

            val validFirstName = firstname?.let { it1 -> checkIfInputIsCorrect(it1) }
            val validLastName = lastname?.let { it1 -> checkIfInputIsCorrect(it1) }
            val validPhone = phone?.let { it1 -> checkIfInputIsCorrect(it1) }

            if (validFirstName == false) {
                feedBackMessage += "\n Invalid firstname"
                binding.editAppTxtCustomerFirstname.error = "Invalid firstname"
            }
            if (validLastName == false){
                feedBackMessage += "\n Invalid lastname"
                binding.editAppTxtCustomerLastname.error = "Invalid lastname"
            }
            if (validPhone == false){
                feedBackMessage += "\n Invalid phone input"
                binding.editAppTxtCustomerPhone.error = "Invalid phone input"
            }

            if (feedBackMessage.isNotEmpty()){
                /* show feedback and do not send into the server */
                Log.e("Feedback: ", feedBackMessage)
                Toast.makeText(requireContext(), feedBackMessage, Toast.LENGTH_LONG).show()
            } else {
                val customer = Customer(_id = null, firstname = firstname, lastname = lastname, phone = phone,
                    email = email)

                viewModel.newCustomer(customer)
                findNavController().navigate(
                    R.id.action_newCustomerFragment_to_customerFragment)
            }

        }
        return binding.root
    }
}