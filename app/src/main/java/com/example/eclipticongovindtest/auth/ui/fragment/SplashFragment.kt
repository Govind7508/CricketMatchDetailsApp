package com.example.eclipticongovindtest.auth.ui.fragment

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.eclipticongovindtest.R
import com.example.eclipticongovindtest.home.data.SharePreferenceDB
import com.example.eclipticongovindtest.home.ui.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (SharePreferenceDB.getSubmitState(requireContext()) == true){
            lifecycleScope.launch {
                delay(5000L)
                val intent = Intent(requireContext(), HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                val animator = ObjectAnimator.ofFloat(view, "translationX", 0f, 100f)
                animator.duration = 500
                animator.start()
            }
        }
       else if (SharePreferenceDB.getWelcomeState(requireContext()) == true) {
            lifecycleScope.launch {
                delay(5000L) // 5 seconds delay
                findNavController().navigate(
                    R.id.loginFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build()
                )
            }

        } else {
            lifecycleScope.launch {
                delay(5000L) // 5 seconds delay
                findNavController().navigate(
                    R.id.welcomeFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.splashFragment, true).build()
                )
            }
        }
    }

}