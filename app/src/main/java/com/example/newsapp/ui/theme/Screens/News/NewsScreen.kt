@file:Suppress("MISSING_DEPENDENCY_CLASS_IN_EXPRESSION_TYPE")

package com.example.newsapp.ui.theme.Screens.News

import android.R
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.newsapp.API.Model.SourcesItemDM
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.newsapp.API.ApiManager
import com.example.newsapp.API.Model.ArticlesItem
import com.example.newsapp.API.Model.NewsResponse
import com.example.newsapp.API.Model.SourcesResponse
import com.example.newsapp.ui.theme.gray
import kotlinx.coroutines.coroutineScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun NewsScreen(categoryApiId: String, modifier: Modifier = Modifier) {

    Column(modifier = Modifier.fillMaxSize()) {
        var Articles = remember { mutableStateListOf<ArticlesItem>() }
        SourcesTabRow(categoryApiId) {
            Articles.clear()
            Articles.addAll(it)
        }
        Spacer(modifier = Modifier.padding(top = 15.dp))
        NewsList(newsList = Articles)

    }
}

@Composable
fun SourcesTabRow(
    categoryApiId: String,
    modifier: Modifier = Modifier,
    onNewsListUpdated: (List<ArticlesItem>) -> Unit
) {
    var sources = remember { mutableStateListOf<SourcesItemDM>() }


    var selectedIndex by remember { mutableStateOf(0) }
    LaunchedEffect(categoryApiId) {
        getSources(categoryApiId) {
            sources.clear()
            sources.addAll(it)
            if (it.isNotEmpty()) {
                getNewsBySource(sources[0].id ?: "") {
                    onNewsListUpdated(it)
                }
            }
        }
    }
    LazyRow {
        itemsIndexed(sources) { index, item ->
            SourcesItem(item, index, selectedIndex) { clickedIndex, sourcesItem ->
                selectedIndex = clickedIndex
                getNewsBySource(sourcesItem.id ?: "") {
                    onNewsListUpdated(it)
                }
            }
        }
    }
}

@Composable
fun SourcesItem(
    sourcesItemDM: SourcesItemDM,
    index: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onSourceClickListener: (Int, SourcesItemDM) -> Unit
) {
    var isSelected by remember { mutableStateOf(index == selectedIndex) }
    if (index == selectedIndex) {
        Text(
            text = sourcesItemDM.name ?: "",
            fontWeight = FontWeight.W900,
            color = Color.Cyan, textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clickable {
                    onSourceClickListener(index, sourcesItemDM)
                }
                .padding(horizontal = 5.dp)
        )
    } else {
        Text(
            text = sourcesItemDM.name ?: "",
            fontWeight = FontWeight.W500,
            textDecoration = TextDecoration.None,
            modifier = Modifier
                .clickable {
                    onSourceClickListener(index, sourcesItemDM)
                }
                .padding(horizontal = 5.dp))
    }
}

fun getSources(categoryApiId: String, onResponse: (List<SourcesItemDM>) -> Unit) {
    ApiManager.getService().getSources(categoryApiId = categoryApiId)
        .enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(
                call: Call<SourcesResponse?>,
                response: Response<SourcesResponse?>
            ) {
                if (response.isSuccessful) {
                    Log.e("ResponseState", "onResponse: ${response.body()?.status}")
                    Log.e("ResponseSources", "onResponse: ${response.body()?.sources}")
                    onResponse(response.body()?.sources ?: listOf())

                } else {
                    Log.e("ResponseError", "onResponse: ${response.code()}")


                }
            }

            override fun onFailure(call: Call<SourcesResponse?>, t: Throwable) {
                Log.e("ResponseState", "onResponse: ${t.message}")

            }
        })
}

fun getNewsBySource(SourceId: String, onResponse: (List<ArticlesItem>) -> Unit) {
    ApiManager.getService().getNewsBySource(sourcesId = SourceId)
        .enqueue(object : Callback<NewsResponse> {
            override fun onResponse(
                call: Call<NewsResponse?>,
                response: Response<NewsResponse?>
            ) {
                if (response.isSuccessful) {
                    onResponse(response.body()?.articles ?: listOf())
                    Log.e("ResponseState", "onResponse: ${response.body()?.status}")
                    Log.e("ResponseArticles", "onResponse: ${response.body()?.articles}")
                }
            }

            override fun onFailure(
                call: Call<NewsResponse?>,
                t: Throwable
            ) {
            }

        })
}

@Composable
fun NewsList(newsList: List<ArticlesItem>) {
    if (newsList.isEmpty()) {
        Text(
            text = "Loading News....",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 25.sp
        )
    } else {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(top = 5.dp)
        ) {
            items(newsList) { ArticlesItem ->
                NewsCard(articlesItem = ArticlesItem)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(modifier: Modifier = Modifier, articlesItem: ArticlesItem) {
    var selectedArticle by remember { mutableStateOf<ArticlesItem?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    if (showBottomSheet && selectedArticle != null) {
        NewsBottomSheet(
            isShowed = showBottomSheet,
            articlesItem = selectedArticle!!

        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .border(
                1.dp, MaterialTheme.colorScheme.onBackground,
                RoundedCornerShape(16.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
   , onClick = {
       selectedArticle=articlesItem
            showBottomSheet =true
        }
        ) {
        GlideImage(
            articlesItem.urlToImage ?: "",
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        )
        Text(
            text = articlesItem.title ?: "",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Row(
            modifier = Modifier
                .padding(top = 5.dp, start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = articlesItem.author ?: "",
                fontSize = 14.sp,
                color = gray,
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            Text(
                text = articlesItem.publishedAt ?: "",
                fontSize = 14.sp,
                color = gray,
                modifier = Modifier.fillMaxWidth(0.5f)
            )

        }


    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun NewsBottomSheet(
    isShowed: Boolean = false,
    articlesItem: ArticlesItem
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(isShowed) }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.onBackground,
            shape = RoundedCornerShape(16.dp), modifier = Modifier.padding(16.dp)


            )
        {
            GlideImage(
                articlesItem.urlToImage ?: "",
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth().fillMaxHeight(0.3f)
                    .clip(shape = RoundedCornerShape(16.dp)), contentScale = ContentScale.FillHeight

            )
            Text(
                text = articlesItem.title ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding( 8.dp),
                color = MaterialTheme.colorScheme.background
            )
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 7.dp),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    disabledContentColor = Color(0),
                    disabledContainerColor = Color(0)
                ), shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "View Full Article", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 10.dp))
            }
        }
    }
}


