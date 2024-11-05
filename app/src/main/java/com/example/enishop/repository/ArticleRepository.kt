package com.example.enishop.repository

import com.example.enishop.bo.Article
import com.example.enishop.dao.ArticleDAO
import com.example.enishop.dao.DAOFactory
import com.example.enishop.dao.DAOType

class ArticleRepository {

    private val articleDAO = DAOFactory.createArticleDAO(DAOType.MEMORY)

    fun getArticle(id: Long): Article? {
        return articleDAO.findById(id)
    }

    fun getAllArticles(): List<Article> {
        return articleDAO.findAll()
    }

    fun addArticle(article: Article): Long {
        return articleDAO.insert(article)
    }
}