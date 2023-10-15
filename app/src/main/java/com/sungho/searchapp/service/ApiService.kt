package com.sungho.searchapp.service

import com.sungho.searchapp.model.ImageSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("search/image")
    suspend fun getImageSearch(
        @Header("Authorization") apiKey:String = "KakaoAK bdc7518f89e90b20121c75fe75a12e08",
        @Query("query") query : String,
        @Query("sort") sort : String = "recency",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
    ): Response<ImageSearchResponse>
}