package com.example.enishop

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.enishop.bo.Article
import com.example.enishop.repository.ArticleRepository
import com.example.enishop.ui.theme.EniShopTheme
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EniShopTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Mylan",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                val articleRepository = ArticleRepository()
                val article = articleRepository.getArticle(1)
                Log.i("MainActivity", "Article: $article")
                val articles = articleRepository.getAllArticles()
                Log.i("MainActivity", "Articles: $articles")
                val newArticle = articleRepository.addArticle(Article(
                    id = 3,
                    name = "Mens Cotton Jacket",
                    description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
                    price = 55.99f,
                    urlImage = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
                    category = "men's clothing",
                    date = Date(),
                ))
                Log.i("MainActivity", "New Article: $newArticle")

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EniShopTheme {
        Greeting("Android")
    }
}