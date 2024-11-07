package com.example.enishop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.enishop.repository.ArticleRepository
import com.example.enishop.ui.screen.ArticleAddScreen
import com.example.enishop.ui.screen.ArticleEditScreen
import com.example.enishop.ui.screen.ArticleListScreen
import com.example.enishop.ui.theme.EniShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EniShopTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val articleRepository = ArticleRepository()
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "articleList") {
                        composable("articleList") {
                            ArticleListScreen(
                                articleRepository = articleRepository,
                                onEditArticle = { article ->
                                    navController.navigate("articleEdit/${article.id}")
                                },
                                onAddArticle = {
                                    navController.navigate("articleAdd")
                                },
                                navController = navController
                            )
                        }
                        composable(
                            "articleEdit/{articleId}",
                            arguments = listOf(navArgument("articleId") { type = NavType.LongType }) // Utilisez LongType
                        ) { backStackEntry ->
                            val articleId = backStackEntry.arguments?.getLong("articleId") ?: 0L
                            Log.d("ArticleEditScreen", "Received articleId: $articleId")
                            val article = articleRepository.getArticleById(articleId)
                            if (article != null) {
                                ArticleEditScreen(
                                    article = article,
                                    articleRepository = articleRepository,
                                    onSave = { updatedArticle ->
                                        articleRepository.updateArticle(updatedArticle)
                                        navController.popBackStack()
                                        navController.navigate("articleList")
                                    },
                                    navController = navController
                                )
                            } else {
                                Log.e("ArticleEditScreen", "Article not found with id: $articleId")
                            }
                        }
                        composable("articleAdd") {
                            ArticleAddScreen(
                                navController = navController,
                                articleRepository = articleRepository
                            )
                        }
                    }
                }
            }
        }
    }
}
