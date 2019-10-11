package br.pprojects.chucknorrisapp.utils

import androidx.room.TypeConverter
import br.pprojects.chucknorrisapp.data.model.Joke
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class DatabaseTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType = object : TypeToken<ArrayList<String>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}