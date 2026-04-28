@file:Suppress("MISSING_DEPENDENCY_CLASS_IN_EXPRESSION_TYPE")

package com.example.newsapp.screens.news

import com.example.domain.Utils.Base.Resource
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.newsapp.ui.theme.gray
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.Entities.News.ArticlesItemEntity
import com.example.domain.Entities.News.SourcesItemEntity
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(categoryApiId: String,navController: NavController) {
    val viewModel: NewsViewModel = koinViewModel()
    Column(modifier = Modifier.fillMaxSize()) {
        var sourceId by remember { mutableStateOf("") }
        SourcesTabRow(categoryApiId = categoryApiId, viewModel = viewModel) {
            sourceId = it
        }
        Spacer(modifier = Modifier.padding(top = 15.dp))
        if (sourceId.isNotBlank()) {
            Log.e("observe", "NewsScreen: bofore NewsList call")
            NewsList(sourceId, viewModel)
        } else {
            Log.e("NewsScreen", "EmptySourceId ")
        }


    }
}@Composable
fun SourcesTabRow(
    categoryApiId: String,
    viewModel: NewsViewModel ,
    onSourceClick: (String) -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    var isInitialized by remember { mutableStateOf(false) }
    val sourceState by viewModel.sourcesResource.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(categoryApiId) {
        viewModel.getSources(categoryApiId)
        isInitialized = false
    }

    LaunchedEffect(sourceState) {
        if (sourceState is Resource.Success<*> && !isInitialized) {
            val sources = (sourceState as Resource.Success<List<SourcesItemEntity>>).data
            if (sources.isNotEmpty()) {
                onSourceClick(sources[0].id ?: "")
                isInitialized = true
            }
        }
    }
    val state = sourceState
    when ( state) {
        is Resource.Initial<*> -> {
            Log.e("SourcesTabRow", "Initial state")
        }

        is Resource.Loading<*> -> {
            Log.e("SourcesTabRow", "Loading state")
            CircularProgressIndicator(color = Color.Cyan, strokeWidth = 10.dp)
        }

        is Resource.Error<*> -> {
            Log.e("SourcesTabRow", "Error: ${state.errorMessage}")
            LaunchedEffect(state.errorMessage) {
                Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        is Resource.Success<*> -> {
            val sources = (state as Resource.Success<List<SourcesItemEntity>>).data
            if (sources.isEmpty()) {
                Text(text = "No sources", color = Color.Red)
            } else {
                LazyRow {
                    itemsIndexed(sources) { index: Int, item: SourcesItemEntity ->
                        Log.e("SourcesTabRow", "Item $index: ${item.name}")
                        SourcesItem(item, index, selectedIndex) { clickedIndex, sourcesItem ->
                            selectedIndex = clickedIndex
                            onSourceClick(sourcesItem.id ?: "")
                        }
                    }
                }
            }
        }

        else -> {}
    }
}
@Composable
fun SourcesItem(
    sourcesItemDM: SourcesItemEntity,
    index: Int,
    selectedIndex: Int,
    onSourceClickListener: (Int, SourcesItemEntity) -> Unit
) {
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


@Composable
fun NewsList(sourceId: String, viewModel: NewsViewModel) {
    val newsList = viewModel.getPagedNews(sourceId).collectAsLazyPagingItems()
    val loadState = newsList.loadState.refresh
    if (sourceId.isEmpty()) return
    when (loadState) {
        is LoadState.Loading -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = Color.Cyan, strokeWidth = 10.dp)
            Text(
                text = "Loading News....",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        is LoadState.Error -> {
            Text(
                text = "Error: ${loadState.error.message}",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 25.sp
            )
            Log.e("Error", "NewsList: Error: ${loadState.error.message}")
        }

        else -> {
            if (newsList.itemCount == 0) {
                Text(
                    text = "No news available",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp)
                ) {
                    items(newsList.itemCount) { index ->
                        newsList[index]?.let {

                                articlesItem ->
                            NewsCard(articlesItem = articlesItem)
                        }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsCard(articlesItem: ArticlesItemEntity) {
    var selectedArticle by remember { mutableStateOf<ArticlesItemEntity?>(null) }
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        onClick = {
            selectedArticle = articlesItem
            showBottomSheet = true
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
    articlesItem: ArticlesItemEntity
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
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .clip(shape = RoundedCornerShape(16.dp)),
                contentScale = ContentScale.FillHeight

            )
            Text(
                text = articlesItem.title ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp),
                color = MaterialTheme.colorScheme.background
            )
            val context = LocalContext.current
            Button(
                onClick = {
                    try {

                        val intent = Intent(Intent.ACTION_VIEW, articlesItem.url?.toUri())
                        context.startActivity(intent)
                        showBottomSheet = false
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "الرابط غير صالح أو لا يمكن فتحه",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                },
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
                Text(
                    text = "View Full Article",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}




