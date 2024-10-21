package com.example.eclipticongovindtest.home.data.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.eclipticongovindtest.databinding.ViewpagerLayoutMatchBinding
import com.example.eclipticongovindtest.home.common.CommonUserMeth
import com.example.eclipticongovindtest.home.data.response.Match

class TournamentMatchAdapter(
    var userMatches: MutableList<Match>) :
    RecyclerView.Adapter<TournamentMatchAdapter.ViewHolder>() {

    class ViewHolder(var binding: ViewpagerLayoutMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewpagerLayoutMatchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return userMatches.size
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val upComingData = userMatches[position]


        holder.apply {
            binding.apply {


                        T20TV.text = upComingData.match_format?:""
                        lineUpOutTV.text = upComingData.tournament_name?:""
                        Team1NameTV.text = upComingData.teams?.a?.name?:""
                        Team2NameTV.text = upComingData.teams?.b?.name?:""
                        CommonUserMeth.setImage(upComingData.teams?.a?.logo_url?:"", Team1IV)
                        CommonUserMeth.setImage(upComingData.teams?.b?.logo_url?:"", Team2IV)
                        CommonUserMeth.setDateTimeInTextViews(upComingData.starts_at?:0L, dateTV, timeTV)

                        team1Background.setBackgroundColor(
                            CommonUserMeth.setBackgroundColor(upComingData.teams?.a?.logo_bg_color?:"")
                        )
                        team2Background.setBackgroundColor(
                            CommonUserMeth.setBackgroundColor(upComingData.teams?.b?.logo_bg_color?:"")
                        )

                        val matchOffers = upComingData.match_offers
                if (matchOffers != null) {
                    if (matchOffers.isNotEmpty()) {
                        offerTV.text = matchOffers.get(0)?.title ?: ""
                        offerDetailsTV.text = matchOffers[0].subtext ?:""
                        CommonUserMeth.setImage(matchOffers[0].offer_icon_url?:"", bottomLogoIV)
                    } else {
                        offerTV.text = "No offers available"
                    }
                }
                    }
                }
            }


    }