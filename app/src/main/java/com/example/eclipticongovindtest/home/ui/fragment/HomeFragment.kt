package com.example.eclipticongovindtest.home.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.eclipticongovindtest.R
import com.example.eclipticongovindtest.databinding.FragmentHomeBinding
import com.example.eclipticongovindtest.home.data.adapter.MainMatchDetailsAdapter
import com.example.eclipticongovindtest.home.data.adapter.UserMatchAdapter
import com.example.eclipticongovindtest.home.data.model.MatchDetailsViewModel
import com.example.eclipticongovindtest.home.data.network.Resource
import com.example.eclipticongovindtest.home.data.response.MatchDataCallBack
import com.example.eclipticongovindtest.home.data.response.MatchXX
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    var matchList: MutableList<MatchDataCallBack> = mutableListOf()
    var userMatchList: MutableList<MatchXX> = mutableListOf()
    private lateinit var binding: FragmentHomeBinding
    private var recyAdp: MainMatchDetailsAdapter? = null
    private var userMatchAdapter: UserMatchAdapter? = null
    private lateinit var handler: Handler
    private val viewModel: MatchDetailsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        handler = Handler(Looper.myLooper()!!)
        viewModel.matchResponse.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Loading ->{
                    binding.progressbar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    viewModel.updateTopBalance(it.value.wallet_summary?.total_balance.toString())

                    var userList : List<MatchXX>? = it.value.user_matches?.match_list
                    if (userList != null) {
                        for(user in userList){
                            userMatchList.add(user)
                        }
                    }
                    setUpTransformer()
                    setUserMatchAdapter(userMatchList)
                    matchList.add(it.value)

                    setUpComingAdapter(matchList)
                            binding.progressbar.visibility = View.GONE
                }


                is Resource.Failure -> {
                    binding.progressbar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Response" + it.errorBody + "Check your Internet connection", Toast.LENGTH_SHORT)
                        .show()

                }


            }
        }
    }

    private fun setUserMatchAdapter(upcomingMatch: MutableList<MatchXX>) {
        userMatchAdapter = UserMatchAdapter(upcomingMatch, binding.highlightVP)

        binding.apply {
            highlightVP.adapter = userMatchAdapter
            highlightVP.offscreenPageLimit = 3
            highlightVP.clipToPadding = false
            highlightVP.clipChildren = false
            highlightVP.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        binding.dotsIndicator.attachTo(binding.highlightVP)

    }

    private fun setUpTransformer() {
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(40))
        transform.addTransformer { page, position ->
            val mth = 1 - abs(position)
            page.scaleY = 0.85f + mth * 0.14f
        }
        binding.highlightVP.setPageTransformer(transform)
        binding.highlightVP.setPageTransformer(transform)
        binding.highlightVP.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,2000)
            }
        })

    }

        override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,2000)
    }
    private val runnable = Runnable {
        binding.highlightVP.currentItem = binding.highlightVP.currentItem + 1
    }
    private fun setUpComingAdapter(matchDetailsCallBack: MutableList<MatchDataCallBack>) {
        binding.MainRCV.layoutManager = LinearLayoutManager(this.context)
        recyAdp = MainMatchDetailsAdapter(matchDetailsCallBack)
        binding.MainRCV.isNestedScrollingEnabled = false

        binding.MainRCV.adapter = recyAdp
    }


}