package com.myproject.anasnewsapp.repository

import com.myproject.anasnewsapp.api.RetrofitInstance
import com.myproject.anasnewsapp.db.ArticleDatabase
import com.myproject.anasnewsapp.model.Article

class NewsRepository(
    val db : ArticleDatabase
) {

    suspend fun getBreakingNews(country : String,page :Int)=
        RetrofitInstance.api.getBreakingNews(country,page)

    suspend fun searchNews(searchQuery : String, pageNumber : Int)=
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)

    suspend fun upsert(article: Article) = db.gerArticleDao().update(article)

    fun getSavedNews() = db.gerArticleDao().getAllArticles()

    suspend fun delete(article: Article) =db.gerArticleDao().deleteArticles(article)

}
