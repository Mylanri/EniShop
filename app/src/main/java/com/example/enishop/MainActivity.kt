package com.example.enishop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.enishop.dao.memory.UserDAOMemoryImpl
import com.example.enishop.repository.ArticleRepository
import com.example.enishop.ui.common.DrawerContent
import com.example.enishop.ui.screen.Article.ArticleAddScreen
import com.example.enishop.ui.screen.Article.ArticleEditScreen
import com.example.enishop.ui.screen.Article.ArticleListScreen
import com.example.enishop.ui.screen.Login.LoginScreen
import com.example.enishop.ui.theme.EniShopTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EniShopTheme(darkTheme = isSystemInDarkTheme()) {
                val articleRepository = ArticleRepository()
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val userDAO = UserDAOMemoryImpl()
                var isLoggedIn by remember { mutableStateOf(false) }

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        DrawerContent(navController = navController,
                            onLogout = {
                                isLoggedIn = false
                                navController.navigate("login")
                            }
                        )
                    }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "articleList"
                    ) { // if (isLoggedIn) "articleList" else "login"
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
                        composable("articleAdd") {
                            ArticleAddScreen(
                                navController = navController,
                                articleRepository = articleRepository
                            )
                        }
//                        composable("account") {
//                            UserDetailScreen()
//                        }
                    }
                }
            }
        }
    }
}
