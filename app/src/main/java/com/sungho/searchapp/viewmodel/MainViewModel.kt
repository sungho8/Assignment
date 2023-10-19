package com.sungho.searchapp.viewmodel

import android.provider.Settings.Global
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungho.searchapp.model.KakaoImage
import com.sungho.searchapp.model.SearchItem
import com.sungho.searchapp.service.MyService
import com.sungho.searchapp.util.Event
import com.sungho.searchapp.util.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    val searchItemList = MutableLiveData<ArrayList<SearchItem>>()
    val searchItemEvent = MutableLiveData<Event<String>>()
    val newItemEvent = MutableLiveData<Event<String>>()

    init {
        searchItemList.value = ArrayList<SearchItem>()
    }

    fun search(keyword : String, page : Int = 1){
        searchItemList.value?.clear()
        load(keyword,page,true)
    }

    fun load(keyword : String, page : Int = 1,isNew : Boolean = false){
        CoroutineScope(Dispatchers.Main).launch {
            val imageRes = MyService.getMyService().getImageSearch(query = keyword, page = page)
            val vclipRes = MyService.getMyService().getVclipSearch(query = keyword, page = page)
            try{
                searchItemList.value?.clear()
                for (image in imageRes.body()?.documents ?: listOf()) {
                    val searchItem = SearchItem(
                        title = image.display_sitename,
                        thumbnail = image.thumbnail_url,
                        dateTime = image.datetime
                    )
                    searchItem.like = GlobalApplication.prefs.likeCheckImg(searchItem)
                    if (GlobalApplication.prefs.likeCheckImg(searchItem))
                        Log.d("isTrue", "${searchItem}")
                    searchItemList.value?.add(searchItem)
                }

                for (vclip in vclipRes.body()?.documents ?: listOf()) {
                    val searchItem = SearchItem(
                        title = vclip.title,
                        thumbnail = vclip.thumbnail,
                        dateTime = vclip.datetime
                    )
                    searchItem.like = GlobalApplication.prefs.likeCheckImg(searchItem)
                    if (GlobalApplication.prefs.likeCheckImg(searchItem))
                        Log.d("isTrue", "${searchItem}")
                    searchItemList.value?.add(searchItem)
                }

                searchItemList.value?.sortByDescending { it.dateTime }
                searchItemEvent.value = Event(if (isNew) "new" else "load")
            }catch (e : Exception){
                searchItemEvent.value = Event("fail")
            }
        }
    }
}