package com.sungho.searchapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.sungho.searchapp.model.ImageSearchResponse
import com.sungho.searchapp.model.KakaoImage
import com.sungho.searchapp.model.SearchItem
import com.sungho.searchapp.model.VclipSearchResponse
import com.sungho.searchapp.service.MyService
import com.sungho.searchapp.util.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response

class MainViewModel : ViewModel(){
    val searchItemList = MutableLiveData<ArrayList<SearchItem>>()
    val searchItemEvent = MutableLiveData<Event<String>>()

    init {
        searchItemList.value = ArrayList<SearchItem>()
    }

    fun imageSearch(keyword : String){
        CoroutineScope(Dispatchers.Main).launch {
            val response = MyService.getMyService().getImageSearch(query = keyword)

            for(image in response.body()?.documents ?: listOf()){
                val searchItem = SearchItem(
                    title = image.display_sitename,
                    thumbnail = image.thumbnail_url,
                    dateTime = image.datetime)
                searchItemList.value?.add(searchItem)
            }
            searchItemEvent.value = Event("success")
        }
    }

    fun vclipSearch(keyword : String){
        CoroutineScope(Dispatchers.Main).launch {
            val response = MyService.getMyService().getVclipSearch(query = keyword)

            Log.d("SearchResult","${response} /  ${response.body()}")

        }
    }
}