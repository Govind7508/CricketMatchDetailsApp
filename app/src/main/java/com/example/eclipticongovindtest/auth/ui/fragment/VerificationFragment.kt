package com.example.eclipticongovindtest.auth.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.eclipticongovindtest.R
import com.example.eclipticongovindtest.home.common.AppConst
import com.example.eclipticongovindtest.home.common.CommonUserMeth
import com.example.eclipticongovindtest.home.data.SharePreferenceDB
import com.example.eclipticongovindtest.databinding.FragmentVerificationBinding

class VerificationFragment : Fragment() {

    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CommonUserMeth.convertToBulletList(
            requireContext(), binding.verificationCodeSendAgainTV,
            AppConst.VERIFICATION_FRAGMENT_TYPE
        )
        binding.editphoneNumberTV.setText(SharePreferenceDB.getPhoneNumber(requireContext()))
        editPhoneNumberAndBackButton()
        submitOTP()
    }

    private fun editPhoneNumberAndBackButton() {
        binding.editphoneNumberTV.setOnClickListener {
            val resultData = SharePreferenceDB.getPhoneNumber(requireContext())
            val resultBundle = Bundle().apply {
                putString("dataKey", resultData)
            }

            setFragmentResult("requestKey", resultBundle)
            findNavController().popBackStack()
        }
        binding.backIM.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun submitOTP() {
        binding.requestOTPBT.setOnClickListener {
            val submitCode =
                "${binding.pin1ET.text}${binding.pin2ET.text}${binding.pin3ET.text}${binding.pin4ET.text}"
            if (submitCode == SharePreferenceDB.getAuthCode(requireContext())) {
                findNavController().navigate(
                    R.id.profileFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.verificationFragment, true).build()
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Your OTP is not Correct Resend again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}