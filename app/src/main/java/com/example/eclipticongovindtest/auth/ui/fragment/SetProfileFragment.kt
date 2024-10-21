package com.example.eclipticongovindtest.auth.ui.fragment

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.eclipticongovindtest.R
import com.example.eclipticongovindtest.auth.data.viewmodel.UserViewModel
import com.example.eclipticongovindtest.auth.model.UserDetailsCallBack
import com.example.eclipticongovindtest.home.common.CommonUserMeth
import com.example.eclipticongovindtest.home.data.SharePreferenceDB
import com.example.eclipticongovindtest.databinding.FragmentSetProfileBinding
import com.example.eclipticongovindtest.home.ui.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetProfileFragment : Fragment(),View.OnClickListener {
    private var _binding: FragmentSetProfileBinding? = null
    private val binding get() = _binding!!
    var selectedImageUri: Uri? = null
    private val userViewModel: UserViewModel by viewModels()
    var phone :String = ""
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetProfileBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        phone = SharePreferenceDB.getPhoneNumber(requireContext()).toString()

        binding.phoneNumberET.setText(phone)
        binding.locationBT.setOnClickListener(this)
        binding.profileIV.setOnClickListener(this)
        binding.backIM.setOnClickListener(this)
        binding.profileSubmitBT.setOnClickListener(this)
        pickImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    selectedImageUri = result.data?.data
                    if (selectedImageUri != null) {
                        handleImageUri(selectedImageUri!!)
                    }
                }
            }
        setProfileDetails()
    }





    fun getEditTextInput() {

        var username = binding.firstNameET.text.toString().trim()
        var lastname = binding.lastNameET.text.toString().trim()

        var location = binding.pincodeET.text.toString()


        when {
            username.isEmpty() -> {
                Toast.makeText(requireContext(), "Enter your UserName", Toast.LENGTH_SHORT).show()
                return
            }
            lastname.isEmpty() -> {
                Toast.makeText(requireContext(), "Enter your Last Name", Toast.LENGTH_SHORT).show()
                return
            }
            phone.isEmpty() -> {
                Toast.makeText(requireContext(), "Enter your Phone Number", Toast.LENGTH_SHORT).show()
                return
            }
            location.isEmpty() -> {
                Toast.makeText(requireContext(), "Enter your Location or Pin", Toast.LENGTH_SHORT).show()
                return
            }
            else ->{
                userViewModel.insertUser(UserDetailsCallBack(username,lastname,phone,location,selectedImageUri))
                val intent = Intent(requireContext(), HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                val animator = ObjectAnimator.ofFloat(view, "translationX", 0f, 100f)
                animator.duration = 500
                animator.start()
            }
        }




    }

private fun setProfileDetails(){
    val isUserEdit = arguments?.getBoolean("isUserEdit") ?: false
    if (isUserEdit) {
        userViewModel.getUser().observe(viewLifecycleOwner) { user ->
            binding.firstNameET.setText(user?.username)
            binding.lastNameET.setText(user?.lastname)
            binding.pincodeET.setText(user?.location)
            binding.phoneNumberET.setText(user?.phone).toString()
            CommonUserMeth.setImage(user?.image.toString(),binding.profileIV)
        }
    } else {
    }

}



    private fun handleImageUri(imageUri: Uri) {
        Glide.with(this)
            .load(imageUri)
            .into(binding.profileIV)
    }


    private fun checkPermissionAndPickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_MEDIA_IMAGES
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    101
                )
            } else {
                CommonUserMeth.pickImage(requireContext(), pickImageLauncher)
            }
        } else {
            // For Android 12 (API level 32) and below
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request permission
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    101
                )
            } else {
                CommonUserMeth.pickImage(requireContext(), pickImageLauncher)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            CommonUserMeth.pickImage(requireContext(), pickImageLauncher)
        } else {
            Toast.makeText(
                requireContext(),
                "Permission denied to read your media.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun onPickImageButtonClicked() {
        checkPermissionAndPickImage()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.locationBT -> {
                Toast.makeText(requireContext(), "Comming soon", Toast.LENGTH_SHORT).show()
            }

            R.id.profileIV -> {
                onPickImageButtonClicked()            }

            R.id.backIM -> {
                findNavController().popBackStack()
            }

            R.id.profileSubmitBT -> {
                getEditTextInput()

            }
        }
    }
}

