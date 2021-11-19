package no.usn.gruppe4.crmwebappandroid.uicomponents.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import no.usn.gruppe4.crmwebappandroid.R
import no.usn.gruppe4.crmwebappandroid.databinding.FragmentEmailResetBinding
import no.usn.gruppe4.crmwebappandroid.uicomponents.LoginViewModel


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
        binding.btnRpSubmit.setOnClickListener{
            viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
            val RpEmailValue = binding.RpEmailValue.text?.toString()
            val RpCodeValue = binding.RpCodeValue.text?.toString()
            if (RpEmailValue != null && RpCodeValue!=null) {
                viewModel.getPasswordResetVerification(RpEmailValue,RpCodeValue)
                findNavController().navigate(R.id.action_emailResetFragment_to_resetPasswordFragment)
            }
        }
        binding.btnRpCancel.setOnClickListener{
            findNavController().navigate(R.id.action_emailResetFragment_to_loginFragment)
        }




        return binding.root
    }


}