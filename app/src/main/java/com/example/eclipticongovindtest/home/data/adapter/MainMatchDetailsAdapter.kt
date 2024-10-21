package com.example.eclipticongovindtest.home.data.adapter
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.eclipticongovindtest.databinding.CommonLayoutBinding
import com.example.eclipticongovindtest.home.data.response.Match
import com.example.eclipticongovindtest.home.data.response.MatchDataCallBack
import com.example.eclipticongovindtest.home.data.response.Offer
import kotlin.math.abs

class MainMatchDetailsAdapter(private var matchDetailsList: MutableList<MatchDataCallBack>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var upcomingMatchListMatch: MutableList<Match> = mutableListOf()
    private var userMatchList: MutableList<Match> = mutableListOf()
    private var offerPosterList: MutableList<Offer> = mutableListOf()



    inner class RecyclerItemViewHolder(private val binding: CommonLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            setUpTransformer(binding)
        }

        fun bindOfferBannerRecyclerView(recyclerItemList: MutableList<Offer>) {
            val adapter = OfferPosterAdapter(recyclerItemList, binding.adsVP)
            binding.apply {
                adsVP.adapter = adapter
                adsVP.offscreenPageLimit = 3
                adsVP.clipToPadding = false
                adsVP.clipChildren = false
                adsVP.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                // Disable nested scrolling to allow vertical scroll
                adsVP.isNestedScrollingEnabled = false
            }
        }

        fun bindLedgeMatchView(recyclerItemList: MutableList<Match>) {
            val userMatchAdapter = TournamentMatchAdapter(recyclerItemList)
            binding.apply {
                ledgeVP.adapter = userMatchAdapter
                ledgeVP.offscreenPageLimit = 3
                ledgeVP.clipToPadding = false
                ledgeVP.clipChildren = false
                ledgeVP.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                ledgeVP.isNestedScrollingEnabled = false
            }
        }

        fun bindUpcomingMatchRecyclerView(recyclerItemList: MutableList<Match>) {
            binding.upcomingRCV.setHasFixedSize(true)
            val snapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(binding.upcomingRCV)
            binding.upcomingRCV.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
            val recyAdp = UpComingMatchDetailsAdapter(recyclerItemList)
            binding.upcomingRCV.adapter = recyAdp
            binding.upcomingRCV.isNestedScrollingEnabled = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = CommonLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecyclerItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return matchDetailsList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val matchData = matchDetailsList[position]

        offerPosterList.clear()
        userMatchList.clear()
        upcomingMatchListMatch.clear()

        offerPosterList.addAll(matchData.current_offers?.offer_list!!)
        for (user in matchData.featured_tournament?.tournament_list!!) {
            userMatchList.addAll(user.match_list!!)
        }
        upcomingMatchListMatch.addAll(matchData.upcoming_matches?.match_list!!)

        val recyclerItemViewHolder = holder as RecyclerItemViewHolder

        recyclerItemViewHolder.bindOfferBannerRecyclerView(offerPosterList)
        recyclerItemViewHolder.bindLedgeMatchView(userMatchList)
        recyclerItemViewHolder.bindUpcomingMatchRecyclerView(upcomingMatchListMatch)
    }

    private fun setUpTransformer(binding: CommonLayoutBinding) {
        val transform = CompositePageTransformer()
        transform.addTransformer(MarginPageTransformer(40))
        transform.addTransformer { page, position ->
            val mth = 1 - abs(position)
            page.scaleY = 0.85f + mth * 0.14f
        }
        binding.ledgeVP.setPageTransformer(transform)
        binding.adsVP.setPageTransformer(transform)
    }
}
