package com.myproject.anasnewsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.myproject.anasnewsapp.R
import com.myproject.anasnewsapp.adapter.NewsAdapter
import com.myproject.anasnewsapp.ui.NewsActivity
import com.myproject.anasnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*

class SaveNewsFragment : Fragment() {


    lateinit var  newsViewModel :NewsViewModel
    lateinit var newsAdapter :NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel= (activity as NewsActivity).newsViewModel
        setUpRV()

        newsAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_saveNewsFragment_to_articalFragment,bundle)
        }


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val possition = viewHolder.adapterPosition
                val  article = newsAdapter.differ.currentList[possition]
                newsViewModel.deleteArticle(article)

                Snackbar.make(view,"Successfuly Deleted article",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        newsViewModel.saveArticle(article)
                    }
                    show()
                }

            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }

        newsViewModel.getSavedNews().observe(viewLifecycleOwner, Observer {
            newsAdapter.differ.submitList(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_news,container,false)
    }

    fun setUpRV() {
        newsAdapter = NewsAdapter()
        rvSavedNews.apply {
            adapter =newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }
}