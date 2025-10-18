package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.Screens.Categories.CategoriesScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.Screens.News.NewsScreen
import com.example.newsapp.Screens.Routes.CategoriesDestination
import com.example.newsapp.Screens.Routes.NewsDestination

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//LaunchedEffect(Unit) {
//    getSources()
//}

            NewsAppTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    topBar = {
                        TopAppBar(
                            topAppBarTitle = "home",
                            onSearchClick = {},
                            onMenuClick = {})
                    }

                ) { innerPadding ->
                    var screenName = rememberSaveable { mutableStateOf("Home") }
                    NavHost(
                        modifier = Modifier.padding(
                            top = innerPadding.calculateTopPadding(),
                            start = 16.dp,
                            end = 16.dp
                        ),
                        navController = navController,
                        startDestination = CategoriesDestination,
                    ) {
                        composable<CategoriesDestination> {
                            CategoriesScreen(navController = navController)
                        }
                        composable<NewsDestination> {
                            val category = it.toRoute<NewsDestination>()
                            NewsScreen(categoryApiId = category.categoryApi)
                            screenName.value = category.categoryApi
                            Log.e("ScreenName", "${category.categoryApi}")
                        }


                    }


                }

            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar(
        topAppBarTitle: String,
        modifier: Modifier = Modifier,
        onMenuClick: () -> Unit,
        onSearchClick: () -> Unit
    ) {
        val title by remember { mutableStateOf(topAppBarTitle) }
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold, fontSize = 25.sp
                )
            },
            //Menu Icon
            navigationIcon = {
                IconButton(onClick = {
                    onMenuClick
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null, modifier = Modifier.size(30.dp)
                    )
                }
            },
            //Search Icon
            actions = {
                IconButton(onClick = {
                    onSearchClick
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(30.dp)
                    )
                }
            },
            colors = TopAppBarColors(
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.onBackground,
                actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                scrolledContainerColor = Color.Transparent
            )
        )
    }
}

//@Preview(showSystemUi = true)
//@Composable
//private fun topAppprev() {
//    TopAppBar("Home", onMenuClick = {}, onSearchClick = {})
//}

