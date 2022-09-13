package com.myproject.anasnewsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id : Int? =null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Serializable

// inherited by serializable means . if u want to pass other than primitive data type like a model class through fragment
// navigation transaction