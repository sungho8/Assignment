package com.sungho.searchapp.fragment

import MyAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sungho.searchapp.R
import com.sungho.searchapp.databinding.FragmentMyBinding
import com.sungho.searchapp.viewmodel.MyViewModel

class MyFragment : Fragment() {
    private lateinit var binding : FragmentMyBinding
    private lateinit var model : MyViewModel

    private val adapter by lazy{ MyAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my,container,false)
        activity?.let {
            binding.lifecycleOwner = this
            model = ViewModelProvider(this).get(MyViewModel::class.java)
            binding.viewModel = model
        }

        setObserver()

        return binding.root
    }

    private fun setObserver(){
        model.myItemEvent.observe(this, Observer{
            adapter.setList(model.myItemList.value ?: arrayListOf())
            binding.itemRecyclerView.adapter = adapter
        })
    }
}