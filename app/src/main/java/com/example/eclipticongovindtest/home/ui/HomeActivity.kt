package com.example.eclipticongovindtest.home.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.eclipticongovindtest.R
import com.example.eclipticongovindtest.databinding.ActivityHomeBinding
import com.example.eclipticongovindtest.home.common.AppConst
import com.example.eclipticongovindtest.home.data.SharePreferenceDB
import com.example.eclipticongovindtest.home.data.model.MatchDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var  navController : NavController
    lateinit var binding: ActivityHomeBinding
    private val viewModel: MatchDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SharePreferenceDB.setSubmitState(true,this)
        viewModel.getMatchDetailsView(AppConst.PAGE_NUMBER)
        viewModel.updateBalance.observe(this) { title ->
            binding.balance.text= title
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }

                R.id.my_match -> {

                    navController.navigate(R.id.myMatchFragment)

                    true
                } R.id.winnerITM -> {

                    navController.navigate(R.id.winnerFragment)

                    true
                }

                R.id.my_space -> {
                    navController.navigate(R.id.profileFragment2)
                    true
                }

                else -> false
            }

        }
    }

    override fun onBackPressed() {
        // Check if the NavController can go back


        // If there are no fragments to pop from the back stack, close the app
        if (navController.currentDestination?.id == R.id.homeFragment) {
            // Finish the activity and close the app
            finish()
        }else if (navController.currentDestination?.id == R.id.myMatchFragment) {
            // Finish the activity and close the app
            finish()
        } else if (navController.currentDestination?.id == R.id.winnerFragment) {
            // Finish the activity and close the app
            finish()
        }else if (navController.currentDestination?.id == R.id.profileFragment2) {
            // Finish the activity and close the app
            finish()
        } else {
            // Let the NavController handle the back press
            super.onBackPressed()
        }
    }
}