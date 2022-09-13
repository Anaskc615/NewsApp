package com.myproject.anasnewsapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.myproject.anasnewsapp.R
import com.myproject.anasnewsapp.adapter.NewsAdapter
import com.myproject.anasnewsapp.ui.NewsActivity
import com.myproject.anasnewsapp.ui.NewsViewModel
import com.myproject.anasnewsapp.utils.Constance.Companion.QUERY_PAGE_SIZE
import com.myproject.anasnewsapp.utils.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment() {

    lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    val TAG = "BreakingNewsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_breaking_news,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as NewsActivity).newsViewModel
        setUpRV()

        newsAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_breakingNewsFragment_to_articalFragment,bundle)
        }

        newsViewModel.breakingNews.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success ->{
                    hideProgressBar()
                    it.data?.let {
                        //Utils List defer cant work with mutable list so thats why added tolist() function at last
                        newsAdapter.differ.submitList(it.articles.toList())
//                        val totalPage =it.totalResults / QUERY_PAGE_SIZE * 2
//                        isLastPage  = newsViewModel.breakingNewsPage ==totalPage
                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    it.data?.let {
//                     Toast.makeText(activity,"An Error Occured : $it",Toast.LENGTH_SHORT).show()
                        Snackbar.make(view,"An Error Occured : $it", Snackbar.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })
    }

    var isLoading= false
//    var isLastPage=false
    var isScrolling=false

    val scrollListener= object :RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            // this means the user is currently scrolling
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling=true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager= recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPossition =layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount =layoutManager.childCount
            val totalItemCount= layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading
            val isAtLastItem = firstVisibleItemPossition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPossition >= 0
            val isTotalMoreThanVisible= totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate =isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate){
                newsViewModel.getBreakingNews("us")
                isScrolling= false
            }
        }
    }

    fun setUpRV() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter =newsAdapter
            layoutManager=LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility =View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility =View.VISIBLE
        isLoading= true
    }
}

