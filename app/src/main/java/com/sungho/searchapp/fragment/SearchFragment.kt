package com.sungho.searchapp.fragment

import SearchAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sungho.searchapp.R
import com.sungho.searchapp.databinding.FragmentSearchBinding
import com.sungho.searchapp.viewmodel.MainViewModel

class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private lateinit var model : MainViewModel

    private val adapter by lazy{ SearchAdapter() }

    var isLoading = false
    var page = 1
    var query = ""

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
        binding.itemRecyclerView.adapter = adapter

        binding.searchButton.setOnClickListener {
            adapter.data.clear()
            query = "${binding.searchEditText.text}"
            page = 1
            model.search(query,page)
        }

        // 스크롤 리스너
        binding.itemRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)?.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                // 스크롤 끝
                if (!isLoading && !binding.itemRecyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    adapter.deleteLoading()
                    model.load(query,++page)
                    isLoading = true
                }
            }
        })
    }

    private fun setObserver(){
        model.searchItemEvent.observe(this, Observer{
            adapter.setList(model.searchItemList.value ?: arrayListOf(),page)
            adapter.notifyItemRangeInserted((page - 1) * 21, 21)    // v10 i10 separator1
            isLoading = false
        })
    }
}