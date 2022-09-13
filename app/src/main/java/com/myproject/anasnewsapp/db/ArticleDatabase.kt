package com.myproject.anasnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myproject.anasnewsapp.model.Article


@Database(
    entities = [Article::class],
    version = 1
)
@TypeConverters(Converter::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun gerArticleDao() : ArticleDao

    companion object {
        @Volatile
        private var instance : ArticleDatabase?= null
        private val LOCK = Any()

        // when constustore call this function will invoke
        operator fun invoke(context : Context)= instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also { instance = it}
        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()

    }
}