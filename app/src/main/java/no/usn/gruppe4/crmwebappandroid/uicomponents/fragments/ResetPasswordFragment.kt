package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEmailResetBinding
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentResetPasswordBinding
import no.usn.gruppe4.crmwebappandroid.models.login.LoginViewModel

class ResetPasswordFragment : Fragment() {
    lateinit var binding: FragmentResetPasswordBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentResetPasswordBinding.inflate(inflater)

        binding.btnRpCancel.setOnClickListener {
            findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
        }
        binding.btnRpSubmit.setOnClickListener{
            viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
            val password = binding.RpPasswordValue.text?.toString()
            if(password != null){
                val newPassword = LoginViewModel.NewPassword(newPassword = password)
                viewModel.setPassword(newPassword)
            }
            findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)

        }


        return binding.root
    }


}