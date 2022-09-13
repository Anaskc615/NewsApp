package com.myproject.anasnewsapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myproject.anasnewsapp.repository.NewsRepository

class NewsViewModelFactoryProvider(
   val app : Application,
   val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(app,newsRepository) as T
    }
}