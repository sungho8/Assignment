package com.sungho.searchapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sungho.searchapp.service.MyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    fun imageSearch(keyword : String){
        CoroutineScope(Dispatchers.Main).launch {
            val response = MyService.getMyService().getImageSearch(query = keyword)
            Log.d("SearchResult","${response} / ${response.headers()} /  ${response.body()} / ${response.errorBody()}")
        }
    }

}