package com.thecommerce.matchingbox.adapter.campaign

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sungho.searchapp.databinding.ItemImageBinding
import com.sungho.searchapp.model.KakaoImage

class ImageAdapter (val type : String) : RecyclerView.Adapter<ImageAdapter.CampaignViewHolder>(){
    var data = listOf<KakaoImage>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignViewHolder {
        val binding = ItemImageBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)

        return CampaignViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        holder.apply {
            holder.onBind(data[position])
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class CampaignViewHolder (val binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data : KakaoImage){
            binding.image = data

            Glide.with(binding.view.context)
                .load(data.image_url)
                .into(binding.imageView)
        }
    }
}