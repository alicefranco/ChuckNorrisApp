package br.pprojects.chucknorrisapp.data.model

import com.google.gson.annotations.SerializedName

data class Joke (
    @SerializedName("categories") var categories: List<String>,
    @SerializedName("icon_url") var iconUrl: String,
    @SerializedName("idl") var id: String,
    @SerializedName("url") var url: String,
    @SerializedName("value") var value: String
)
