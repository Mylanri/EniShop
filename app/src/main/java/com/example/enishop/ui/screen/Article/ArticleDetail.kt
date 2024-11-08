package com.example.enishop.ui.screen.Article

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.enishop.bo.Article
import com.example.enishop.repository.ArticleRepository
import com.example.enishop.ui.common.TopBar
import com.example.enishop.vm.ArticleDetailViewModel

@Composable
fun ArticleDetailScreen(
    modifier: Modifier = Modifier, article: Article, navController: NavController
) {

    Scaffold(topBar = {
        TopBar(
            navController = navController,
            onLogout = {},
            darkTheme = false,
            onThemeToggle = {})
    }) {

        Column(
            modifier = modifier.padding(it)
        ) {
            ArticleDetail(article.id, navController = NavController(LocalContext.current))
        }
    }
}

@Composable
fun ArticleDetail(
    articleId: Long,
    navController: NavController,
    articleRepository: ArticleRepository = ArticleRepository(),
) {
    val viewModel: ArticleDetailViewModel = viewModel(
        factory = ArticleDetailViewModel.ArticleDetailViewModelFactory(articleRepository)
    )
    LaunchedEffect(articleId) {
        viewModel.loadArticle(articleId)
    }
    val article = viewModel.article
    val context = LocalContext.current
    article?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.clickable {
                    val searchIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/search?q=${it.name} Eni-Shop")
                    )
                    context.startActivity(searchIntent)
                })
            Spacer(modifier = Modifier.height(8.dp))
            it.urlImage?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = "Image de l'article",
                    modifier = Modifier.size(128.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Price : ${it.price} €",
                modifier = Modifier.testTag("ARTICLE_PRICE_TAG")
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.description)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                navController.navigate("articleList")
            }) {
                Text(text = "Back to articles")
            }
        }
    } ?: run {
        Text(text = "Chargement en cours...")
    }
}