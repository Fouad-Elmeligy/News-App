package com.example.newsapp.DataModel

import com.example.newsapp.R
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDM(
    val title: Int? = null,
    val image: Int? = null,
    val apiId: String? = null
) {
    companion object {
        fun getCategoriesList(): List<CategoryDM> {
            return listOf(
                CategoryDM(title = R.string.general, image =R.drawable.general, apiId ="general"),

                CategoryDM(title =R.string.business, image =R.drawable.business, apiId ="business"),

                CategoryDM(title =R.string.sports, image =R.drawable.sports, apiId ="sports"),

                CategoryDM(title =R.string.technology, image = R.drawable.technology, apiId ="technology"),

                CategoryDM(title =R.string.entertainment, image = R.drawable.entertainment, apiId ="entertainment"),

                CategoryDM(title =R.string.science, image = R.drawable.scinece, apiId ="science"),

                CategoryDM(title =R.string.medicine, image = R.drawable.medicine, apiId ="health")

                )
        }
    }
}
