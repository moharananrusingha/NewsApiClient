package com.oit.newsapiclient.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oit.newsapiclient.R
import com.oit.newsapiclient.data.util.Resource
import com.oit.newsapiclient.databinding.FragmentNewsBinding
import com.oit.newsapiclient.presentation.adapter.NewsAdapter
import com.oit.newsapiclient.presentation.viewmodel.NewsViewModel

class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter

    private val countryName = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        initRecyclerView()
        viewNewsList()
    }

    private fun viewNewsList() {
        viewModel.getNewsHeadLine(countryName, page)
        viewModel.newsHeadLine.observe(viewLifecycleOwner) {response ->
            when(response) {
                is Resource.Loading -> {
                    showProgressBar()

                }
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        if(it.totalResults % 20 == 0) {
                            pages = it.totalResults / 20
                        } else {
                            pages = it.totalResults / 20 + 1
                        }

                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An Error Occurred: $it", Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvNews.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = newsAdapter
            this.addOnScrollListener(this@NewsFragment.onScrollListener)
        }

    }

    private fun showProgressBar() {
        isLoading = true
        binding.pbNews.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        binding.pbNews.visibility = View.INVISIBLE
    }

    private val onScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = binding.rvNews.layoutManager as LinearLayoutManager

            val sizeOfCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfCurrentList

            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if(shouldPaginate) {
                page++
                viewModel.getNewsHeadLine(countryName, page)
                isScrolling = false
                Log.i("MyTag", "Fetching page number: $page")

            }
        }
    }
}