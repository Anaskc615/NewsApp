package com.myproject.anasnewsapp.db

import androidx.room.TypeConverter
import com.myproject.anasnewsapp.model.Source

class Converter {

    @TypeConverter
    fun fromSource(source: Source) : String{
        return source.name
    }

    @TypeConverter
    fun toSource(string: String) : Source {
        return Source(string,string)
    }
}