package com.example.eclipticongovindtest.home.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eclipticongovindtest.R
import com.example.eclipticongovindtest.auth.data.viewmodel.UserViewModel
import com.example.eclipticongovindtest.databinding.FragmentProfileBinding
import com.example.eclipticongovindtest.home.common.CommonUserMeth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile),View.OnClickListener  {
    private lateinit var binding: FragmentProfileBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        binding.profileSubmitBT.setOnClickListener(this)
        userViewModel.getUser().observe(viewLifecycleOwner) { user ->
            binding.firstNameTextV.setText(user?.username)
            binding.lastNameTextV.setText(user?.lastname)
            binding.pincodeTextV.setText(user?.location)
            binding.phoneNumberTextV.setText(user?.phone).toString()
            binding.fullName.setText("${user?.username} " +" "+ "${user?.lastname}")
            CommonUserMeth.setImage(user?.image.toString(),binding.profileImage)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.profileSubmitBT -> {
                val bundle = Bundle().apply {
                    putBoolean("isUserEdit", true)
                }
                findNavController().navigate(R.id.action_profileFragment2_to_setProfileFragment2, bundle)
//                val isUserEdit = true
//                val action = ProfileFragmentDirections.actionProfileFragment2ToSetProfileFragment2()
//                findNavController().navigate(
//                    R.id.setProfileFragment,
//                    null,
//                    NavOptions.Builder().setPopUpTo(R.id.profileFragment, true).build()
//                )
            }
        }
    }

}