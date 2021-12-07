package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEmailResetBinding
import no.usn.gruppe4.crmwebappandroid.models.login.ResetPasswordRequest
import no.usn.gruppe4.crmwebappandroid.models.login.LoginViewModel


class EmailResetFragment : Fragment() {
 lateinit var binding:FragmentEmailResetBinding
 private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEmailResetBinding.inflate(inflater)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_emailResetFragment_to_loginFragment)
        }
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.btnRpSendEmail.setOnClickListener{
            var ok = true
            if(!isFilled(binding.RpEmailValue)) ok = false
            if(!checkEmail(binding.RpEmailValue)) ok = false
            if (ok){
                val RpEmailValue = binding.RpEmailValue.text?.toString()
                if (RpEmailValue != null){
                    viewModel.resetPassword(ResetPasswordRequest(RpEmailValue))
                    binding.RpCode.visibility = View.VISIBLE
                    binding.btnRpSubmit.visibility = View.VISIBLE
                    val text = getString(R.string.See_e_mail_for_reset_code)
                    val duration = Toast.LENGTH_LONG

                    val toast = Toast.makeText(context, text, duration)
                    toast.show()
                }
            }
        }
        binding.btnRpSubmit.setOnClickListener{
            var ok = true
            if(!isFilled(binding.RpEmailValue)) ok = false
            if(!isFilled(binding.RpCodeValue)) ok = false
            if(!checkEmail(binding.RpEmailValue)) ok = false
            if(ok){
                val RpEmailValue = binding.RpEmailValue.text?.toString()
                val RpCodeValue = binding.RpCodeValue.text?.toString()
                if (RpEmailValue != null && RpCodeValue != null) {
                    viewModel.getPasswordResetVerification(RpEmailValue, RpCodeValue)
                    viewModel.success.observe(viewLifecycleOwner,{
                        val test = it
                        if(test){
                            val text = getString(R.string.code_verified)
                            val duration = Toast.LENGTH_LONG

                            val toast = Toast.makeText(context, text, duration)
                            toast.show()
                            findNavController().navigate(R.id.action_emailResetFragment_to_resetPasswordFragment)
                        }else{
                            binding.RpCodeValue.error = getString(R.string.code_not_verified)
                        }
                    })

                }
            }
        }
        binding.btnRpCancel.setOnClickListener{
            findNavController().navigate(R.id.action_emailResetFragment_to_loginFragment)
        }




        return binding.root
    }

    private fun isFilled(element: TextInputEditText): Boolean{
        var res: Boolean
        if (element.text!!.isEmpty() ){
            element.error = "Required!"
            res =  false
        }else{
            res =  true
        }
        return res;
    }
    private fun checkEmail(element: TextInputEditText): Boolean {
        var res: Boolean
        /* https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/-regex/ */
        val emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        res = element.text!!.matches(Regex(emailPattern))
        if(!res){
            element.error = "invalid email address"
        }

        return res
    }
}