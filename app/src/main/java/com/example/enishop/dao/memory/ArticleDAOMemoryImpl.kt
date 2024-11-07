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
            isFavorite = true
        ),
        Article(
            id = 2,
            name = "Mens Casual Premium Slim Fit T-Shirts",
            description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
            price = 22.3f,
            urlImage = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            category = "men's clothing",
            date = Date(),
            isFavorite = false
        ),
        Article(
            id = 3,
            name = "Women's Snowboard Jacket Waterproof",
            description = "Stay warm and dry with this waterproof, windproof jacket designed for the slopes. Features a detachable hood, zipper pockets, and adjustable cuffs.",
            price = 89.99f,
            urlImage = "https://fakestoreapi.com/img/71hblAHs5xL._AC_UY879_-2.jpg",
            category = "women's clothing",
            date = Date(),
            isFavorite = false
        ),
        Article(
            id = 4,
            name = "DANVOUY Women's T-Shirt Casual Cotton Short Sleeve",
            description = "95% cotton, 5% spandex. Great for casual wear, perfect fit, and comfort with a round neckline and a soft touch.",
            price = 12.99f,
            urlImage = "https://fakestoreapi.com/img/61pHAEJ4NML._AC_UX679_.jpg",
            category = "women's clothing",
            date = Date(),
            isFavorite = false
        ),
        Article(
            id = 5,
            name = "Solid Gold Petite Micropave",
            description = "Sleek and elegant micropave design crafted in solid gold with stunning cubic zirconia. Perfect for special occasions.",
            price = 168.0f,
            urlImage = "https://fakestoreapi.com/img/71yae2x5V2L._AC_UL640_QL65_ML3_.jpg",
            category = "jewelery",
            date = Date(),
            isFavorite = false
        ),
        Article(
            id = 6,
            name = "White Gold Plated Princess",
            description = "A beautiful princess-cut white gold plated ring with crystal accents, designed to make you feel like royalty.",
            price = 9.99f,
            urlImage = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg",
            category = "jewelery",
            date = Date(),
            isFavorite = true
        ),
        Article(
            id = 7,
            name = "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",
            description = "Inspired by the mythical water dragon that protects the ocean's pearl, this bracelet symbolizes love and abundance.",
            price = 695.0f,
            urlImage = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg",
            category = "jewelery",
            date = Date(),
            isFavorite = true
        ),
        Article(
            id = 8,
            name = "WD 2TB Elements Portable External Hard Drive",
            description = "Reliable and fast USB 3.0 storage for your PC and Mac. Perfect for extra storage or backup.",
            price = 64.99f,
            urlImage = "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg",
            category = "electronics",
            date = Date(),
            isFavorite = false
        ),
        Article(
            id = 9,
            name = "SanDisk SSD PLUS 1TB Internal SSD",
            description = "Boost your PC’s performance with up to 535MB/s read speeds for fast and responsive performance.",
            price = 109.0f,
            urlImage = "https://fakestoreapi.com/img/61U7T1koQqL._AC_SX679_.jpg",
            category = "electronics",
            date = Date(),
            isFavorite = true
        ),
        Article(
            id = 10,
            name = "Samsung 49-Inch CHG90 144Hz Curved Gaming Monitor",
            description = "Ultra-wide 49-inch monitor with a 144Hz refresh rate and Quantum Dot technology for enhanced color accuracy.",
            price = 999.99f,
            urlImage = "https://fakestoreapi.com/img/81Zt42ioCgL._AC_SX679_.jpg",
            category = "electronics",
            date = Date(),
            isFavorite = true
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

    override fun update(article: Article) {
        val index = articlesInMemory.indexOfFirst { it.id == article.id }
        if (index != -1) {
            articlesInMemory[index] = article
        }
    }

    override fun delete(article: Article) {
        val isDeleted = articlesInMemory.remove(article)
        if (!isDeleted) {
            throw IllegalArgumentException("Article not found")
        }
    }
}