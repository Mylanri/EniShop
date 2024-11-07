package com.example.enishop.ui.screen.Article

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    var showFavoritesOnly by remember { mutableStateOf(false) }
    val categories = listOf("electronics", "jewelery", "men's clothing", "women's clothing")
    var searchQuery by remember { mutableStateOf("") }

    var filteredArticles = articles.filter { article ->
        (selectedCategory == null || article.category == selectedCategory) &&
                (!showFavoritesOnly || article.isFavorite)
    }

    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var articleToDelete by remember { mutableStateOf<Article?>(null) }

    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                drawerState = DrawerState(DrawerValue.Open),
                onLogout = {},
                darkTheme = false,
                onThemeToggle = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onAddArticle() }) {
                Icon(Icons.Default.Add, contentDescription = "Ajouter un article")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding)
        ) {
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { query ->
                    searchQuery = query
                    filteredArticles = articles.filter { it.name.contains(searchQuery, ignoreCase = true) }
                }
            )
            CategoryFilter(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    selectedCategory = if (selectedCategory == category) null else category
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = showFavoritesOnly,
                    onCheckedChange = { showFavoritesOnly = it }
                )
                Text(
                    text = "Favoris",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp
                )
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
                            articleToDelete = article
                            showDeleteConfirmationDialog = true
                        },
                        onFavoriteChange = { isFavorite ->
                            article.isFavorite = isFavorite
                            articles = articles.map {
                                if (it.id == article.id) it.copy(isFavorite = isFavorite) else it
                            }
                        }
                    )
                }
            }
        }
    }

    // Popup de confirmation de suppression
    if (showDeleteConfirmationDialog && articleToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            title = { Text("Confirmation") },
            text = { Text("Êtes-vous sûr de vouloir supprimer cet article ?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Supprimer l'article et fermer la popup
                        articleRepository.deleteArticle(articleToDelete!!)
                        navController.popBackStack()
                        articles = articleRepository.getAllArticles()
                        showDeleteConfirmationDialog = false
                        Log.i("ArticleListScreen", "Article supprimé: ${articleToDelete!!}")
                    }
                ) {
                    Text("Oui")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        // Fermer la popup sans supprimer l'article
                        showDeleteConfirmationDialog = false
                    }
                ) {
                    Text("Non")
                }
            }
        )
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChanged,
        label = { Text("Find articles by name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Rechercher")
        }
    )
}

@Composable
fun CategoryFilter(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(categories.size) { index ->
            val category = categories[index]
            TextButton(
                onClick = {
                    onCategorySelected(category)
                },
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(
                        if (selectedCategory == category) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                    )
                    .padding(vertical = 3.dp, horizontal = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (selectedCategory == category) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Catégorie sélectionnée",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (selectedCategory == category) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
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
    onFavoriteChange: (Boolean) -> Unit
) {
    val cardModifier = Modifier
        .padding(4.dp)
        .size(220.dp)
        .clickable { onEditClick() }

    Card(
        modifier = cardModifier
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.name,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp
                )
                val navController = NavController(LocalContext.current)
                IconButton(
                    onClick = {
                        onFavoriteChange(!article.isFavorite)
                        navController.popBackStack();
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    val favoriteIcon = if (article.isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Filled.FavoriteBorder
                    }
                    Icon(
                        imageVector = favoriteIcon,
                        contentDescription = "Marquer comme favori",
                        tint = if (article.isFavorite) Color.Red else Color.Gray
                    )
                }
            }
            if (article.urlImage.isNotEmpty()) {
                Image(
                    painter = rememberImagePainter(article.urlImage),
                    contentDescription = "Image de ${article.name}",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${article.price} €",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onEditClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit",
                        modifier = Modifier.height(24.dp)
                    )
                }
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(45.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
        }
    }
}

