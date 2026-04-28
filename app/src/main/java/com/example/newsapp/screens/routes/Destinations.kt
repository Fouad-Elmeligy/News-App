package com.example.newsapp.screens.routes

import kotlinx.serialization.Serializable

@Serializable
object CategoriesDestination


@Serializable
data class NewsDestination(val categoryApi: String)

