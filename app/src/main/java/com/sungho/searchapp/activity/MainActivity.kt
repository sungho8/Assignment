package com.sungho.searchapp.activity

import SearchAdapter
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sungho.searchapp.R
import com.sungho.searchapp.databinding.ActivityMainBinding
import com.sungho.searchapp.viewmodel.MainViewModel
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var model : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(MainViewModel::class.java)

        model.imageSearch("하스스톤")
        model.vclipSearch("수박")

        setObserver()
    }

    private fun setObserver(){
        model.searchItemEvent.observe(this, Observer{
            val adapter = SearchAdapter()
            adapter.data = model.searchItemList.value ?: listOf()
            binding.imageRecyclerView.adapter = adapter
        })
    }
}