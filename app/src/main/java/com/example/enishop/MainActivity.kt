package com.example.enishop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.enishop.dao.memory.UserDAOMemoryImpl
import com.example.enishop.repository.ArticleRepository
import com.example.enishop.ui.screen.Article.ArticleAddScreen
import com.example.enishop.ui.screen.Article.ArticleDetailScreen
import com.example.enishop.ui.screen.Article.ArticleEditScreen
import com.example.enishop.ui.screen.Article.ArticleListScreen
import com.example.enishop.ui.screen.Login.LoginScreen
import com.example.enishop.ui.theme.EniShopTheme
import com.example.enishop.vm.ArticleListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EniShopApp()
        }
    }
}

@Composable
fun EniShopApp() {
    // Detect if the system is in dark theme or light theme
    val systemInDarkTheme = isSystemInDarkTheme()

    // Use `remember` to store the theme state
    var darkTheme by remember { mutableStateOf(systemInDarkTheme) }

    // Apply the theme
    EniShopTheme(darkTheme = darkTheme) {
        val articleRepository = ArticleRepository() // Repository initialization
        val navController = rememberNavController()
        val userDAO = UserDAOMemoryImpl()
        var isLoggedIn by remember { mutableStateOf(false) }

        // Navigation Setup
        NavHost(
            navController = navController,
            startDestination = "articleList"
        ) {
            // Login Screen
            composable("login") {
                LoginScreen(
                    navController = navController,
                    userDAO = userDAO,
                    onLoginSuccess = {
                        isLoggedIn = true
                        navController.navigate("articleList")
                    }
                )
            }
            composable("articleList") {
                ArticleListScreen(
                    onEditArticle = { article ->
                        navController.navigate("articleEdit/${article.id}")
                    },
                    onAddArticle = {
                        navController.navigate("articleAdd")
                    },
                    navController = navController,
                )
            }
            composable(
                "articleDetail/{articleId}",
                arguments = listOf(navArgument("articleId") { type = NavType.LongType })
            ) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getLong("articleId") ?: 0L
                ArticleDetailScreen(
                    article = articleRepository.getArticleById(articleId)!!,
                    navController = navController
                )
            }
            composable(
                "articleEdit/{articleId}",
                arguments = listOf(navArgument("articleId") { type = NavType.LongType })
            ) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getLong("articleId") ?: 0L
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
                }
            }

            // Article Add Screen
            composable("articleAdd") {
                ArticleAddScreen(
                    navController = navController,
                    articleRepository = articleRepository
                )
            }
        }
    }
}