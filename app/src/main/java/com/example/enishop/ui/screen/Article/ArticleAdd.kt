package com.example.enishop.ui.screen.Article

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.enishop.bo.Article
import com.example.enishop.dao.ArticleDAO
import com.example.enishop.repository.ArticleRepository
import com.example.enishop.ui.common.TopBar
import java.util.Date

@Composable
fun ArticleAddScreen(
    modifier: Modifier = Modifier,
    articleRepository: ArticleRepository,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopBar(
                navController,
                drawerState = DrawerState(DrawerValue.Closed),
                onLogout = {},
                darkTheme = false,
                onThemeToggle = {}
                )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            ArticleCreationForm { article ->
                articleRepository.addArticle(article)
                navController.popBackStack()
                navController.navigate("articleList")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleCreationForm(onSubmit: (Article) -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var urlImage by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(Date()) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val isFavorite = remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("electronics", "jewelery", "men's clothing", "women's clothing")

    val context = LocalContext.current
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
            text = "Création d'un nouvel article",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        ArticleTextField(
            value = name,
            onValueChange = { name = it },
            label = "Nom"
        )
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
        val isFavorite = remember { mutableStateOf(false) }
        val context = LocalContext.current
        LaunchedEffect(isFavorite.value) {
            if (isFavorite.value) {
                Toast.makeText(context, "Ajouté aux favoris", Toast.LENGTH_SHORT).show()
                Log.i("ArticleCreationForm", "Article ajouté aux favoris")
            } else {
                Toast.makeText(context, "Retirer des favoris", Toast.LENGTH_SHORT).show()
                Log.i("ArticleCreationForm", "Article retiré des favoris")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isFavorite.value,
                onCheckedChange = { isFavorite.value = it }
            )
            Text(
                text = "Favori ?",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val article = Article(
                    id = 1,
                    name = name,
                    description = description,
                    price = price.toFloatOrNull() ?: 0.0f,
                    urlImage = urlImage,
                    category = category,
                    date = date,
                    isFavorite = isFavorite.value
                )
                onSubmit(article)
                Log.i("ArticleCreationForm", "Article créé: $article")
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Créer l'article")
        }
    }
}

@Composable
fun ArticleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ArticleCreationScreen(articleDAO: ArticleDAO) {
    ArticleCreationForm { article ->
        articleDAO.insert(article)
    }
}
