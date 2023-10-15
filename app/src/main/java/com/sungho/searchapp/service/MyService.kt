package com.sungho.searchapp.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MyService {
    fun getMyService():ApiService{
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1000, TimeUnit.SECONDS)
            .readTimeout(1000, TimeUnit.SECONDS)
            .writeTimeout(1000, TimeUnit.SECONDS)
            .addInterceptor(AppInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    class AppInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
            val REST_API_KEY = "7639bbb9b6c722c27d81bdd8f915d78e"
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "KakaoAK ${REST_API_KEY}")
                .build()
            proceed(newRequest)
        }
    }
}