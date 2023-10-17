package com.sungho.searchapp.search

import SearchAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sungho.searchapp.R
import com.sungho.searchapp.databinding.FragmentSearchBinding
import com.sungho.searchapp.viewmodel.MainViewModel

class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private lateinit var model : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search,container,false)
        activity?.let {
            binding.lifecycleOwner = this
            model = ViewModelProvider(this).get(MainViewModel::class.java)
            binding.viewModel = model
        }

        setListener()
        setObserver()

        return binding.root
    }

    private fun setListener(){
        binding.searchButton.setOnClickListener {
            val query = "${binding.searchEditText.text}"
            model.imageSearch(query)
        }
    }

    private fun setObserver(){
        model.searchItemEvent.observe(this, Observer{
            val adapter = SearchAdapter()
            adapter.data = model.searchItemList.value ?: listOf()
            binding.imageRecyclerView.adapter = adapter
        })
    }
}