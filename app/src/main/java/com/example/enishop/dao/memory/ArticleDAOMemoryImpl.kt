package com.example.enishop.dao.memory

import com.example.enishop.bo.Article
import com.example.enishop.dao.ArticleDAO
import java.util.Date

class ArticleDAOMemoryImpl : ArticleDAO {
    private val articlesInMemory = mutableListOf<Article>(
        Article(
            id = 1,
            name = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
            price = 109.95f,
            urlImage = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            category = "men's clothing",
            date = Date(),
        ),
        Article(
            id = 2,
            name = "Mens Casual Premium Slim Fit T-Shirts",
            description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
            price = 22.3f,
            urlImage = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            category = "men's clothing",
            date = Date(),
        ),
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
}