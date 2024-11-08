package com.example.enishop.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.enishop.bo.Article
import com.example.enishop.repository.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ArticleListViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _filteredArticles = MutableStateFlow<List<Article>>(emptyList())
    val filteredArticles: StateFlow<List<Article>> = _filteredArticles

    private var _selectedCategory: String? = null
    var _showFavoritesOnly: Boolean = false
    private var _searchQuery: String = ""

    init {
        _categories.value = listOf("electronics", "jewelery", "men's clothing", "women's clothing")
        loadArticles()
    }

    private fun loadArticles() {
        _articles.value = articleRepository.getAllArticles()
        applyFilters()
    }

    fun applyFilters() {
        var filteredList = _articles.value

        filteredList = filterByCategory(filteredList)
        filteredList = filterByFavorites(filteredList)
        filteredList = filterBySearchQuery(filteredList)

        _filteredArticles.value = filteredList
    }

    private fun filterByCategory(articles: List<Article>): List<Article> {
        return if (_selectedCategory != null) {
            articles.filter { it.category == _selectedCategory }
        } else {
            articles
        }
    }

    private fun filterByFavorites(articles: List<Article>): List<Article> {
        return if (_showFavoritesOnly) {
            articles.filter { it.isFavorite }
        } else {
            articles  // Si les favoris ne sont pas activés, tous les articles sont affichés
        }
    }

    private fun filterBySearchQuery(articles: List<Article>): List<Article> {
        return if (_searchQuery.isNotEmpty()) {
            articles.filter { it.name.contains(_searchQuery, ignoreCase = true) }
        } else {
            articles  // Si la recherche est vide, ne filtre pas par nom
        }
    }

    fun setSelectedCategory(category: String?) {
        _selectedCategory = if (_selectedCategory == category) null else category
        applyFilters()
    }

    fun toggleFavorite(article: Article) {
        val updatedArticle = article.copy(isFavorite = !article.isFavorite)
        articleRepository.updateArticle(updatedArticle)
        loadArticles()
    }

    fun setShowFavoritesOnly(showFavorites: Boolean) {
        _showFavoritesOnly = if (_showFavoritesOnly == showFavorites) false else showFavorites
        applyFilters()
    }

    fun setSearchQuery(query: String) {
        _searchQuery = query
        applyFilters()
    }

    fun deleteArticle(article: Article) {
        articleRepository.deleteArticle(article)
        loadArticles()
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return ArticleListViewModel(
                    ArticleRepository()
                ) as T
            }
        }
    }
}
