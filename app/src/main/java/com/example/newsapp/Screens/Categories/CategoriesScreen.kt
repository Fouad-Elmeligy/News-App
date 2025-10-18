package com.example.newsapp.Screens.Categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.DataModel.CategoryDM
import com.example.newsapp.R
import com.example.newsapp.Screens.Routes.NewsDestination
import com.example.newsapp.ui.theme.ViewAllButtonBgColor

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(modifier = modifier) {
        Text(
            text = "Good Morning \n Here is Some News For You",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 6.dp)
        )
        CategoryList(navController = navController)
    }
}

@Composable
fun CategoryList(modifier: Modifier = Modifier, navController: NavHostController) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        itemsIndexed(CategoryDM.getCategoriesList()) { index, item ->
            if (index % 2 == 0)
                this@LazyColumn.IsEvenIndex(item, onCategoryClick = {
                    navController.navigate(NewsDestination(item.apiId ?: "", item.title ?: 0))
                })
            else
                this@LazyColumn.IsOddIndex(item, onCategoryClick = {
                    navController.navigate(NewsDestination(item.apiId ?: "", item.title ?: 0))
                })

        }
    }

}

@Composable
fun LazyListScope.IsEvenIndex(item: CategoryDM, onCategoryClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(13.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground

        ), onClick = {
            onCategoryClick()
        }
    ) {
        Row {
            Image(
                painter = painterResource(item.image ?: R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.5f),
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.05f))
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(item.title ?: R.string.home),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 27.sp
                )
                EvenCategoryButton()
            }
        }
    }
}

@Composable
fun LazyListScope.IsOddIndex(item: CategoryDM, onCategoryClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(13.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground

        ), onClick = {
            onCategoryClick()
        }
    ) {
        Row(horizontalArrangement = Arrangement.End) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
            ) {
                Text(
                    text = stringResource(item.title ?: R.string.home),
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 30.sp
                )
                OddCategoryButton()
            }
            Image(
                painter = painterResource(item.image ?: R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillBounds
            )
        }
    }

}


@Composable
fun EvenCategoryButton(text: String = "View All  ", modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        colors = ButtonColors(
            containerColor = ViewAllButtonBgColor,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContentColor = Color(0),
            disabledContainerColor = Color(0)
        ), modifier = Modifier
            .width(150.dp)
            .height(50.dp), contentPadding = PaddingValues(0.dp)
    ) {
        Text(text = text, fontSize = 20.sp, modifier = Modifier.padding(start = 20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End // Start أو End أو Center
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.background,
                    shape = CircleShape
                )
            ) {
                Icon(
                    painterResource(R.drawable.caret_right),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun OddCategoryButton(text: String = "View All  ", modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        colors = ButtonColors(
            containerColor = ViewAllButtonBgColor,
            contentColor = MaterialTheme.colorScheme.onBackground,
            disabledContentColor = Color(0),
            disabledContainerColor = Color(0)
        ), modifier = Modifier
            .width(150.dp)
            .height(50.dp), contentPadding = PaddingValues(0.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically      // Start أو End أو Center
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.background,
                    shape = CircleShape
                )
            ) {
                Icon(
                    painterResource(R.drawable.caret_left),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(text = text, fontSize = 20.sp)
        }


    }

}


@Composable
fun circleImageButtonColor(modifier: Modifier = Modifier): Color {
    if (isSystemInDarkTheme())
        return Color.Black
    return Color.White
}