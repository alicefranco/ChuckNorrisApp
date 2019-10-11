package br.pprojects.chucknorrisapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Joke (
    @PrimaryKey
    @SerializedName("id") var id: String,
    @SerializedName("categories") var categories: List<String>,
    @SerializedName("icon_url") var iconUrl: String,
    @SerializedName("url") var url: String,
    @SerializedName("value") var value: String
)
