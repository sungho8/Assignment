package com.sungho.searchapp.service

import com.sungho.searchapp.model.ImageSearchResponse
import com.sungho.searchapp.model.VclipSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("search/image")
    suspend fun getImageSearch(
        @Query("query") query : String,
        @Query("sort") sort : String = "recency",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
    ): Response<ImageSearchResponse>

    @GET("search/vclip")
    suspend fun getVclipSearch(
        @Query("query") query : String,
        @Query("sort") sort : String = "recency",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
    ): Response<VclipSearchResponse>
}