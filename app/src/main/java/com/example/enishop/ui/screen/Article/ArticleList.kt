package com.example.enishop.ui.screen.Article

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.enishop.bo.Article
import com.example.enishop.ui.common.TopBar
import com.example.enishop.vm.ArticleListViewModel
import android.widget.Toast

@Composable
fun ArticleListScreen(
    modifier: Modifier = Modifier,
    onEditArticle: (Article) -> Unit,
    onAddArticle: () -> Unit,
    navController: NavController,
    viewModel: ArticleListViewModel = viewModel(factory = ArticleListViewModel.Factory)
) {
    val articles by viewModel.articles.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val filteredArticles by viewModel.filteredArticles.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBar(
                navController,
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
            // Search Bar
            SearchBar(
                searchQuery = searchQuery,
                onSearchQueryChanged = { query ->
                    searchQuery = query
                    viewModel.setSearchQuery(query)
                }
            )

            // Filter by Category
            CategoryFilter(
                categories = categories,
                selectedCategory = null,
                onCategorySelected = { category ->
                    viewModel.setSelectedCategory(category)
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icône dynamique avec IconButton pour activer ou désactiver l'état de favoris
                IconButton(
                    onClick = {
                        // Inverser l'état du favori
                        viewModel.setShowFavoritesOnly(!viewModel._showFavoritesOnly)
                    }
                ) {
                    Icon(
                        imageVector = if (viewModel._showFavoritesOnly) {
                            Icons.Filled.Favorite  // Cœur rempli quand l'état est activé
                        } else {
                            Icons.Filled.FavoriteBorder  // Cœur vide quand l'état est désactivé
                        },
                        contentDescription = "Favoris",
                        modifier = Modifier.padding(start = 8.dp),
                        tint = if (viewModel._showFavoritesOnly) Color.Red else Color.Gray // Facultatif : changer la couleur
                    )
                }

                // Texte pour expliquer ce que fait l'icône
                Text(
                    text = "Favoris",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp
                )
            }


            // Display the articles
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
                            viewModel.deleteArticle(article)
                        },
                        onFavoriteChange = { isFavorite ->
                            viewModel.toggleFavorite(article)  // Mettre à jour le favori
                            navController.popBackStack()
                        },
                        navController = navController
                    )
                }
            }
        }
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
                    onCategorySelected(category)  // Cette méthode va gérer la sélection/désélection
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
    onFavoriteChange: (Boolean) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current  // Utiliser LocalContext pour afficher un Toast

    val cardModifier = Modifier
        .padding(4.dp)
        .size(220.dp)
        .clickable {
            navController.navigate("articleDetail/${article.id}")
        }

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

                // Button for favorite
                IconButton(
                    onClick = {
                        onFavoriteChange(!article.isFavorite)  // Toggle favorite
                        // Afficher un Toast pour confirmer
                        val message = if (article.isFavorite) {
                            "Article retiré des favoris"
                        } else {
                            "Article ajouté aux favoris"
                        }
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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

