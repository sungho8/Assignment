package com.sungho.searchapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sungho.searchapp.model.KakaoImage
import com.sungho.searchapp.service.MyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    val imageList = MutableLiveData<ArrayList<KakaoImage>>()

    fun imageSearch(keyword : String){
        CoroutineScope(Dispatchers.Main).launch {
            val response = MyService.getMyService().getImageSearch(query = keyword)
            Log.d("SearchResult","${response} / ${response.headers()} /  ${response.body()} / ${response.errorBody()}")
        }
    }

    fun vclipSearch(keyword : String){
        CoroutineScope(Dispatchers.Main).launch {
            val response = MyService.getMyService().getVclipSearch(query = keyword)
            Log.d("SearchResult","${response} / ${response.headers()} /  ${response.body()} / ${response.errorBody()}")
        }
    }
}