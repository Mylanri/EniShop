package com.example.enishop.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.enishop.bo.Article
import com.example.enishop.repository.ArticleRepository
import com.example.enishop.ui.common.TopBar

@Composable
fun ArticleListScreen(
    modifier: Modifier = Modifier,
    articleRepository: ArticleRepository,
    onEditArticle: (Article) -> Unit,
    onAddArticle: () -> Unit,
    navController: NavController
) {
    var articles by remember { mutableStateOf(articleRepository.getAllArticles()) }

    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val categories = listOf("electronics", "jewelery", "men's clothing", "women's clothing")
    val filteredArticles = if (selectedCategory != null) {
        articles.filter { it.category == selectedCategory }
    } else {
        articles
    }

    Scaffold(
        topBar = { TopBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddArticle() }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter un article")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            LazyRow(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.size) { index ->
                    val category = categories[index]
                    TextButton(
                        onClick = {
                            // Si la catégorie est déjà sélectionnée, désélectionne-la
                            selectedCategory = if (selectedCategory == category) null else category
                        }
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (selectedCategory == category) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredArticles.size) { index ->
                    val article = filteredArticles[index]
                    ArticleItem(
                        article = article,
                        onEditClick = { onEditArticle(article) },
                        onDeleteClick = {
                            articleRepository.deleteArticle(article)
                            navController.popBackStack()
                            articles = articleRepository.getAllArticles()
                            Log.i("ArticleListScreen", "Article supprimé: $article")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    val cardModifier = Modifier
        .padding(4.dp)
        .size(200.dp)
        .clickable { onEditClick() }

    Card(
        modifier = cardModifier
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = article.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp
            )
            if (article.urlImage.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(article.urlImage),
                    contentDescription = "Image de ${article.name}",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                )
            }
            Text(
                text = "Prix: ${article.price} €",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = onEditClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                ) {
                    Text("Edit")
                }
                TextButton(
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}
