package com.example.eclipticongovindtest.home.data.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.eclipticongovindtest.R
import com.example.eclipticongovindtest.databinding.OfferBannerCardLayoutBinding
import com.example.eclipticongovindtest.home.common.CommonUserMeth
import com.example.eclipticongovindtest.home.data.response.Offer

class OfferPosterAdapter(var offerList: MutableList<Offer>, private val viewpager :ViewPager2) :
    RecyclerView.Adapter<OfferPosterAdapter.ViewHolder>() {
    class ViewHolder(var binding: OfferBannerCardLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            OfferBannerCardLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return offerList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offerListData = offerList[position]

        holder.apply {
            binding.apply {
                CommonUserMeth.setImage((offerListData.offer_banner_url?: R.drawable.splash_background).toString(),offerPosterIV)
                }
            }

if (position == offerList.size -1){
    viewpager.post(runnable)
}
}

    private val runnable = Runnable {
        offerList.addAll(offerList)
        notifyDataSetChanged()
    }

}