package com.example.eclipticongovindtest.auth.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.eclipticongovindtest.R
import com.example.eclipticongovindtest.home.common.AppConst
import com.example.eclipticongovindtest.home.common.CommonUserMeth
import com.example.eclipticongovindtest.home.data.SharePreferenceDB
import com.example.eclipticongovindtest.databinding.FragmentLoginBinding

class LoginFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    var result: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("requestKey") { requestKey, bundle ->
            result = bundle.getString("dataKey")
        }
        if (result != null) {
            binding.loginPhoneNumberET.setText(SharePreferenceDB.getPhoneNumber(requireContext()))
        }
        CommonUserMeth.convertToBulletList(
            requireContext(),
            binding.loginTermandCnditionTV,
            AppConst.LOGIN_FRAGMENT_TYPE
        )

        binding.backIM.setOnClickListener(this)
        binding.requestOTPBT.setOnClickListener(this)
        binding.googleTV.setOnClickListener(this)
        binding.facebookTV.setOnClickListener(this)

    }

    fun getOtp(view: View) {
        var phoneNumber: String? = null
        if (!binding.loginPhoneNumberET.text.toString().isEmpty()) {
            phoneNumber = binding.loginPhoneNumberET.text.toString().trim()

        } else {
        }
        val firstTwo = phoneNumber?.substring(0, 2) // First two digits
        val lastTwo = phoneNumber?.takeLast(2) // Last two digits
        var code: String = "0000"
        code = firstTwo + lastTwo

        if (phoneNumber?.length?.equals("") != null) {
            if (phoneNumber?.length!! < 10) {
                Toast.makeText(
                    requireContext(),
                    "Input is too short Min 10 Digit number",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                SharePreferenceDB.setPhoneNumber(phoneNumber, requireContext())
                SharePreferenceDB.setAuthCode(code, requireContext())
                CommonUserMeth.showPopup(
                    findNavController(),
                    requireContext(),
                    view,
                    SharePreferenceDB.getAuthCode(requireContext()).toString(),
                    AppConst.LOGIN_FRAGMENT_TYPE
                )

            }
        } else {
            Toast.makeText(requireContext(), "Enter your Phone Number", Toast.LENGTH_SHORT).show()

        }

    }



    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.googleTV -> {
                Toast.makeText(requireContext(), "Comming soon", Toast.LENGTH_SHORT).show()
            }

            R.id.facebookTV -> {
                Toast.makeText(requireContext(), "Comming soon", Toast.LENGTH_SHORT).show()
            }

            R.id.backIM -> {
                requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        requireActivity().finishAffinity()
                    }
                })
            }

            R.id.requestOTPBT -> {
                getOtp(v)
            }
        }
    }


}