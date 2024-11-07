package com.example.enishop.dao.network

import com.example.enishop.bo.Article
import com.example.enishop.dao.ArticleDAO
import java.util.Date

class ArticleDAONetworkImpl : ArticleDAO {

    private val articlesInMemory = mutableListOf<Article>(
        Article(
            id = 3,
            name = "Mens Cotton Jacket",
            description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
            price = 55.99f,
            urlImage = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
            category = "men's clothing",
            date = Date(),
        )
    )

    override fun findById(id: Long): Article? {
        return articlesInMemory.find { it.id == id }
    }

    override fun insert(article: Article): Long {
        articlesInMemory.add(article)
        return article.id
    }

    override fun findAll(): List<Article> {
        return articlesInMemory
    }

    override fun delete(article: Article) {
        val isDeleted = articlesInMemory.remove(article)
        if (!isDeleted) {
            throw IllegalArgumentException("Article not found")
        }
    }

    override fun update(article: Article) {
        val index = articlesInMemory.indexOfFirst { it.id == article.id }
        if (index != -1) {
            articlesInMemory[index] = article
        }
    }
}