package com.example.enishop.dao

import com.example.enishop.bo.Article

interface ArticleDAO {
    fun findById(id: Long): Article?
    fun insert(article: Article): Long
    fun findAll(): List<Article>
    fun delete(article: Article)
    fun update(article: Article)
}