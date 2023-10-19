package com.sungho.searchapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.sungho.searchapp.model.LikeSet
import com.sungho.searchapp.model.SearchItem
import com.sungho.searchapp.util.Event
import com.sungho.searchapp.util.GlobalApplication

class MyViewModel : ViewModel(){
    val myItemList = MutableLiveData<ArrayList<SearchItem>>()
    val myItemEvent = MutableLiveData<Event<String>>()

    init {
        myItemList.value = arrayListOf()
        getSaveImage()
    }

    fun getSaveImage(){
        val gson = GsonBuilder().create()
        val likeJson = GlobalApplication.prefs.getString("likeSet","")
        val likeSet = if(likeJson == "") LikeSet()
        else gson.fromJson(likeJson,LikeSet::class.java)    // prefs에서 불러온 Set

        for(item in likeSet.mutableSet){
            Log.d("LikeItem","${likeJson}")
            myItemList.value?.add(item)
        }

        myItemEvent.value = Event<String>("success")
        Log.d("LikeItemSet","${myItemList.value}")
    }
}