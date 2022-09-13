package com.myproject.anasnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.myproject.anasnewsapp.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(article: Article) : Long

    @Query("SELECT * FROM articles")
    fun getAllArticles() : LiveData<List<Article>>

    @Delete
    suspend fun deleteArticles(article: Article)
}