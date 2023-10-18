package com.sungho.searchapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungho.searchapp.model.KakaoImage
import com.sungho.searchapp.model.SearchItem
import com.sungho.searchapp.service.MyService
import com.sungho.searchapp.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    val searchItemList = MutableLiveData<ArrayList<SearchItem>>()
    val searchItemEvent = MutableLiveData<Event<String>>()

    init {
        searchItemList.value = ArrayList<SearchItem>()
    }

    fun search(keyword : String, page : Int = 1){
        searchItemList.value?.clear()
        load(keyword,page)
    }

    fun load(keyword : String, page : Int = 1){
        CoroutineScope(Dispatchers.Main).launch {
            val imageRes = MyService.getMyService().getImageSearch(query = keyword,page = page)
            val vclipRes = MyService.getMyService().getVclipSearch(query = keyword,page = page)

            searchItemList.value?.clear()
            for(image in imageRes.body()?.documents ?: listOf()){
                val searchItem = SearchItem(
                    title = image.display_sitename,
                    thumbnail = image.thumbnail_url,
                    dateTime = image.datetime)
                searchItemList.value?.add(searchItem)
            }

            for(vclip in vclipRes.body()?.documents ?: listOf()){
                val searchItem = SearchItem(
                    title = vclip.title,
                    thumbnail = vclip.thumbnail,
                    dateTime = vclip.datetime)
                searchItemList.value?.add(searchItem)
            }

            searchItemList.value?.sortByDescending { it.dateTime }
            searchItemEvent.value = Event("success")
        }
    }
}