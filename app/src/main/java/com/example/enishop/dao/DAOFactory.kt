package com.example.enishop.dao

import com.example.enishop.dao.memory.ArticleDAOMemoryImpl
import com.example.enishop.dao.network.ArticleDAONetworkImpl

abstract class DAOFactory {
    abstract fun createArticleDAO(): ArticleDAO
    companion object {
        fun createArticleDAO(type: DAOType): ArticleDAO {
            return when (type) {
                DAOType.MEMORY -> ArticleDAOMemoryImpl()
                DAOType.NETWORK -> ArticleDAONetworkImpl()
            }
        }
    }
}