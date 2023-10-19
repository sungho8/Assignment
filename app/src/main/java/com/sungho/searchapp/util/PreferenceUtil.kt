package com.sungho.searchapp.util
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.ImageView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.sungho.searchapp.R
import com.sungho.searchapp.model.LikeSet
import com.sungho.searchapp.model.SearchItem
import java.lang.reflect.Type

class PreferenceUtil (context: Context){
    private val gson by lazy { GsonBuilder().create() }

    val prefs : SharedPreferences = context.getSharedPreferences("prefs_name",Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }


    fun setLikeBtn(imageView: ImageView, like : Boolean){
        val res = if(like) R.drawable.ic_like else R.drawable.ic_unlike
        imageView.setImageResource(res)
    }

    fun addLikeImg(imageView: ImageView, item : SearchItem){
        // set 불러오기
        val likeJson = getString("likeSet","")
        val likeSet = if(likeJson == "") LikeSet()
            else gson.fromJson(likeJson,LikeSet::class.java)    // prefs에서 불러온 Set

        // 새로운 데이터 저장
        likeSet.mutableSet.add(item)

        // set 저장
        setString("likeSet",gson.toJson(likeSet))

        // 좋아요 버튼
        setLikeBtn(imageView,true)
    }

    fun removeLikeImg(imageView: ImageView, item : SearchItem){
        // set 불러오기
        val likeJson = getString("likeSet","")
        val likeSet = if(likeJson == "") LikeSet()
        else gson.fromJson(likeJson,LikeSet::class.java)    // prefs에서 불러온 Set

        // 해당 데이터 제거
        item.like = false
        likeSet.mutableSet.remove(item)

        // set 저장
        setString("likeSet",gson.toJson(likeSet))

        setLikeBtn(imageView,false)
    }

    fun likeCheckImg(item: SearchItem) : Boolean{
        // set 불러오기
        val likeJson = getString("likeSet","")
        val likeSet = if(likeJson == "") LikeSet()
        else gson.fromJson(likeJson,LikeSet::class.java)    // prefs에서 불러온 Set

        return likeSet.mutableSet.contains(item)
    }
}