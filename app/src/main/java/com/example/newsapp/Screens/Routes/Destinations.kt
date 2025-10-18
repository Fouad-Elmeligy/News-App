package com.example.newsapp.Screens.Routes

import com.example.newsapp.DataModel.CategoryDM
import kotlinx.serialization.Serializable

@Serializable
object CategoriesDestination


@Serializable
data class NewsDestination(val categoryApi: String,val title: Int)