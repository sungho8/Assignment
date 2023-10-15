package com.thecommerce.matchingbox.adapter.campaign

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sungho.searchapp.model.KakaoImage

object ImageBindingAdapter {
    @BindingAdapter("items")
    @JvmStatic
    fun setItems(recyclerView : RecyclerView, items : ArrayList<KakaoImage>){
        if(recyclerView.adapter == null){
            val adapter = ImageAdapter("current")
            adapter.setHasStableIds(true)
            recyclerView.adapter = adapter
        }

        val myAdapter = recyclerView.adapter as ImageAdapter

        myAdapter.data = items
        myAdapter.notifyDataSetChanged()    // notifyItemRangeInserted()로 수정하는게 바람직함
    }
}