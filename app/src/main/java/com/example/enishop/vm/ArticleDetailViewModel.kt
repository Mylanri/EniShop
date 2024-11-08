package com.example.enishop.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.enishop.bo.Article
import com.example.enishop.repository.ArticleRepository
import kotlinx.coroutines.launch

class ArticleDetailViewModel(private val articleRepository: ArticleRepository) : ViewModel() {
    private var _article: Article? = null
    val article: Article?
        get() = _article

    fun loadArticle(articleId: Long) {
        viewModelScope.launch {
            _article = articleRepository.getArticleById(articleId)
        }
    }

    class ArticleDetailViewModelFactory(
        private val articleRepository: ArticleRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArticleDetailViewModel::class.java)) {
                return ArticleDetailViewModel(articleRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
