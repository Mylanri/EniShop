package com.example.enishop.bo

import java.util.Date

data class Article(
    val id: Long,
    val name: String,
    val description: String,
    val price: Float,
    val urlImage: String,
    val category: String,
    val date: Date,
    var isFavorite: Boolean = false
) {
    override fun toString(): String {
        return "Article(id=$id, name='$name', description='$description', price=$price, urlImage='$urlImage', category='$category', date=$date, isFavorite=$isFavorite)"
    }
}
