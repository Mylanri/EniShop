package com.example.enishop.dao

import com.example.enishop.dao.memory.ArticleDAOMemoryImpl
import com.example.enishop.dao.memory.UserDAOMemoryImpl
import com.example.enishop.dao.network.ArticleDAONetworkImpl
import com.example.enishop.dao.network.UserDAONetworkImpl

abstract class DAOFactory {
    abstract fun createArticleDAO(): ArticleDAO
    companion object {
        fun createArticleDAO(type: DAOType): ArticleDAO {
            return when (type) {
                DAOType.MEMORY -> ArticleDAOMemoryImpl()
                DAOType.NETWORK -> ArticleDAONetworkImpl()
            }
        }
        fun createUserDAO(type: DAOType): UserDAO {
            return when (type) {
                DAOType.MEMORY -> UserDAOMemoryImpl()
                DAOType.NETWORK -> UserDAONetworkImpl()
            }
        }
    }
}