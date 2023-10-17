package com.sungho.searchapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.sungho.searchapp.R
import com.sungho.searchapp.databinding.ActivityMainBinding
import com.sungho.searchapp.search.MyFragment
import com.sungho.searchapp.search.SearchFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTabLayout()
    }

    private fun setTabLayout(){
        supportFragmentManager.beginTransaction().add(R.id.frameLayout,SearchFragment()).commit()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0-> replaceView(SearchFragment())
                    1-> replaceView(MyFragment())
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun replaceView(tab: Fragment) {
        tab?.let {
            supportFragmentManager.beginTransaction().replace(
                R.id.frameLayout, it
            ).commit()
        }
    }
}