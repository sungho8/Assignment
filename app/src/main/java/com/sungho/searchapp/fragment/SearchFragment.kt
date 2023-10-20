package com.sungho.searchapp.fragment

import SearchAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sungho.searchapp.R
import com.sungho.searchapp.databinding.FragmentSearchBinding
import com.sungho.searchapp.viewmodel.SearchViewModel

class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private lateinit var model : SearchViewModel

    var adapter = SearchAdapter()

    var isLoading = false
    var page = 1
    var query = ""
    var totalCnt = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search,container,false)
        activity?.let {
            binding.lifecycleOwner = this
            model = ViewModelProvider(this).get(SearchViewModel::class.java)
            binding.viewModel = model
        }

        setListener()
        setObserver()

        return binding.root
    }

    private fun setListener(){
        binding.itemRecyclerView.adapter = adapter

        binding.searchButton.setOnClickListener {
            query = "${binding.searchEditText.text}"
            page = 1
            totalCnt = 0
            model.search(query,page)

            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity?.window?.decorView?.applicationWindowToken, 0)
        }

        // 스크롤 리스너
        binding.itemRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)?.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1)

                if(model.isVclipEnd && model.isImgEnd) {
                    adapter.deleteLoading()
                    return
                }
                // 스크롤 끝
                if (!isLoading && !binding.itemRecyclerView.canScrollVertically(1)
                    && lastVisibleItemPosition == itemTotalCount && model.searchItemList.value?.size != 0) {
                    adapter.deleteLoading()
                    model.load(query,++page)
                    isLoading = true
                }
            }
        })
    }

    private fun setObserver(){
        model.searchItemEvent.observe(this, Observer{
            // 검색 결과가 0개일때
            val size = model.searchItemList.value?.size ?: 0

            if(size == 0) {
                Toast.makeText(requireContext(),"검색 결과가 없습니다.",Toast.LENGTH_SHORT).show()
                return@Observer
            }

            if(it.peekContent() == "new"){
                adapter = SearchAdapter()
                adapter.setList(model.searchItemList.value ?: arrayListOf(),page,model.isImgEnd,model.isVclipEnd)
                binding.itemRecyclerView.adapter = adapter
            }else if(it.peekContent() == "load"){
                totalCnt += page * (size + 1)
                adapter.setList(model.searchItemList.value ?: arrayListOf(),page,model.isImgEnd,model.isVclipEnd)
                adapter.notifyItemRangeInserted(totalCnt, size + 1)
                isLoading = false
            }else{
                Toast.makeText(requireContext(),"검색을 실패했습니다.",Toast.LENGTH_SHORT).show()
            }
        })
    }
}