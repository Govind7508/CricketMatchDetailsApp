package com.example.eclipticongovindtest.home.data.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.eclipticongovindtest.databinding.ViewpagerUserMatchLayoutMatchBinding
import com.example.eclipticongovindtest.home.common.CommonUserMeth
import com.example.eclipticongovindtest.home.common.CommonUserMeth.Companion.getTexColor
import com.example.eclipticongovindtest.home.common.CommonUserMeth.Companion.setBackgroundColor
import com.example.eclipticongovindtest.home.common.CommonUserMeth.Companion.setDateTimeInTextViews
import com.example.eclipticongovindtest.home.common.CommonUserMeth.Companion.updateStatusTextView
import com.example.eclipticongovindtest.home.data.response.MatchXX

class UserMatchAdapter(
    var userMatches: MutableList<MatchXX>, var viewPager: ViewPager2
) :
    RecyclerView.Adapter<UserMatchAdapter.ViewHolder>() {

    class ViewHolder(var binding: ViewpagerUserMatchLayoutMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ViewpagerUserMatchLayoutMatchBinding.inflate(
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

                T20TV.text = upComingData.match?.match_format
                lineUpOutTV.text = upComingData.match?.tournament_name
                Team1NameTV.text = upComingData.match?.teams?.a?.name ?: ""

                updateStatusTextView(
                    holder.itemView.context,
                    upComingData.match?.status,
                    statusTextView
                )

                Team2NameTV.text = upComingData.match?.teams?.b?.name ?: ""
                CommonUserMeth.setImage(upComingData.match?.teams?.a?.logo_url ?: "", Team1IV)
                CommonUserMeth.setImage(upComingData.match?.teams?.b?.logo_url ?: "", Team2IV)

                setDateTimeInTextViews(
                    upComingData.match?.starts_at ?: 0L,
                    dateTV,
                    timeTV
                )

                team1Background.setBackgroundColor(
                    setBackgroundColor(
                        upComingData.match?.teams?.a?.logo_bg_color ?: ""
                    )
                )
                team2Background.setBackgroundColor(
                    setBackgroundColor(
                        upComingData.match?.teams?.b?.logo_bg_color ?: ""
                    )
                )
                var join = "JOININGS(${upComingData.user_teams})"
                getTexColor(join, holder.itemView.context, joining)

                var contestV = "CONTESTS(${upComingData.user_contests})"
                getTexColor(contestV, holder.itemView.context, contest)
                if (upComingData.top_running_rank == null) {
                    rank.text = ""
                    rank.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

                } else {
                    rank.text = "TOP RUNNING RANKING : ${upComingData.top_running_rank}"
                }
            }
        }
        if (position == userMatches.size -1){
            viewPager.post(runnable)
        }
    }

    private val runnable = Runnable {
        userMatches.addAll(userMatches)
        notifyDataSetChanged()
    }

}