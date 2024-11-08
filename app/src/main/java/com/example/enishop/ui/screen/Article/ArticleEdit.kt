package com.example.enishop.ui.screen.Article

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.enishop.bo.Article
import com.example.enishop.repository.ArticleRepository
import com.example.enishop.ui.common.TopBar
import java.util.Date

@Composable
fun ArticleEditScreen(
    modifier: Modifier = Modifier,
    article: Article,
    articleRepository: ArticleRepository,
    onSave: (Article) -> Unit,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopBar(
                navController,
                onLogout = {},
                darkTheme = false,
                onThemeToggle = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            ArticleForm(
                initialName = article.name,
                initialDescription = article.description,
                initialPrice = article.price.toString(),
                initialCategory = article.category,
                initialUrlImage = article.urlImage,
                initialId = article.id,
                initialIsFavorite = article.isFavorite,
                onSubmit = { updatedArticle ->
                    articleRepository.updateArticle(updatedArticle)
                    onSave(updatedArticle)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleForm(
    initialName: String = "",
    initialDescription: String = "",
    initialPrice: String = "",
    initialCategory: String = "",
    initialUrlImage: String = "",
    initialId: Long = 0,
    initialIsFavorite: Boolean = false,
    onSubmit: (Article) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var description by remember { mutableStateOf(initialDescription) }
    var price by remember { mutableStateOf(initialPrice) }
    var category by remember { mutableStateOf(initialCategory) }
    var urlImage by remember { mutableStateOf(initialUrlImage) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isFavorite by remember { mutableStateOf(initialIsFavorite) }

    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("electronics", "jewelery", "men's clothing", "women's clothing")

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        urlImage = uri.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Modification de l'article",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        ArticleTextField(value = name, onValueChange = { name = it }, label = "Nom")
        ArticleTextField(
            value = description,
            onValueChange = { description = it },
            label = "Description"
        )
        ArticleTextField(
            value = price,
            onValueChange = { price = it },
            label = "Prix",
            keyboardType = KeyboardType.Number
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Catégorie") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            category = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Choisir une image")
        }
        selectedImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isFavorite,
                onCheckedChange = { isFavorite = it }
            )
            Text("Favori", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val updatedArticle = Article(
                    id = initialId,
                    name = name,
                    description = description,
                    price = price.toFloatOrNull() ?: 0.0f,
                    urlImage = urlImage,
                    category = category,
                    date = Date(),
                    isFavorite = isFavorite
                )
                onSubmit(updatedArticle)
                Log.i("ArticleForm", "Article updated: $updatedArticle")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Sauvegarder les modifications")
        }
    }
}
