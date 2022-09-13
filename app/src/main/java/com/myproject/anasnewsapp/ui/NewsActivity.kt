package com.myproject.anasnewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.myproject.anasnewsapp.R
import com.myproject.anasnewsapp.db.ArticleDatabase
import com.myproject.anasnewsapp.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {


    lateinit var newsViewModel :NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsRepository =NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory =NewsViewModelFactoryProvider(application,newsRepository)
        newsViewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)
        //this was old vesion
//        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())

        //new version implimentation
        val navHostFragment= supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController= navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }
}