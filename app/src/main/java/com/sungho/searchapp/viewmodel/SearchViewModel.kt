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

    fun search(keyword : String, page : Int = 1){
        isImgEnd = false
        isVclipEnd = false
        searchItemList.value?.clear()
        load(keyword,page,true)
    }

    fun load(keyword : String, page : Int = 1,isNew : Boolean = false){
        CoroutineScope(Dispatchers.Main).launch {
            val imageRes = MyService.getMyService().getImageSearch(query = keyword, page = page)
            val vclipRes = MyService.getMyService().getVclipSearch(query = keyword, page = page)

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

                Log.d("Search Result Image","$page ${imageRes} ${imageRes.body()}")
                Log.d("Search Result Vclip","$page ${vclipRes} ${vclipRes.body()}")
                Log.d("Total Result","$page ${searchItemList.value?.size} ${isImgEnd} ${isVclipEnd}")
                searchItemList.value?.sortByDescending { it.dateTime }
                searchItemEvent.value = Event(if (isNew) "new" else "load")
            }catch (e : Exception){
                searchItemEvent.value = Event("fail")
            }
        }
    }
}