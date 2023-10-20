package com.sungho.searchapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungho.searchapp.model.SearchItem
import com.sungho.searchapp.service.MyService
import com.sungho.searchapp.util.Event
import com.sungho.searchapp.util.GlobalApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel(){
    val searchItemList = MutableLiveData<ArrayList<SearchItem>>()
    val searchItemEvent = MutableLiveData<Event<String>>()

    var isImgEnd = false
    var isVclipEnd = false

    init {
        searchItemList.value = ArrayList<SearchItem>()
    }

    fun search(query : String, page : Int = 1){
        isImgEnd = false
        isVclipEnd = false
        searchItemList.value?.clear()
        load(query,page,true)
    }

    fun load(query : String, page : Int = 1, isNew : Boolean = false){
        CoroutineScope(Dispatchers.Main).launch {
            val imageRes = MyService.getMyService().getImageSearch(query = query, page = page)
            val vclipRes = MyService.getMyService().getVclipSearch(query = query, page = page)

            try{
                searchItemList.value?.clear()
                // 이미지
                if(!isImgEnd){
                    isImgEnd = imageRes.body()?.metaData?.isEnd ?: false
                    for (image in imageRes.body()?.documents ?: listOf()) {
                        val searchItem = SearchItem(
                            title = image.display_sitename,
                            thumbnail = image.thumbnail_url,
                            dateTime = image.datetime
                        )
                        searchItem.like = GlobalApplication.prefs.likeCheckImg(searchItem)
                        searchItemList.value?.add(searchItem)
                    }
                }

                // 동영상
                if(!isVclipEnd){
                    isVclipEnd = vclipRes.body()?.metaData?.isEnd ?: false
                    for (vclip in vclipRes.body()?.documents ?: listOf()) {
                        val searchItem = SearchItem(
                            title = vclip.title,
                            thumbnail = vclip.thumbnail,
                            dateTime = vclip.datetime
                        )
                        searchItem.like = GlobalApplication.prefs.likeCheckImg(searchItem)
                        searchItemList.value?.add(searchItem)
                    }
                }

                searchItemList.value?.sortByDescending { it.dateTime }
                searchItemEvent.value = Event(if (isNew) "new" else "load")
            }catch (e : Exception){
                searchItemEvent.value = Event("fail")
            }
        }
    }
}