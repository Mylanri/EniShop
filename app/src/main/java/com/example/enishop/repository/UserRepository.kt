package com.example.enishop.repository

import com.example.enishop.bo.User
import com.example.enishop.dao.DAOFactory
import com.example.enishop.dao.DAOType

class UserRepository {

    private val userDAO = DAOFactory.createUserDAO(DAOType.MEMORY)

    fun getUser(id: Long): User? {
        return userDAO.findById(id)
    }

    fun getAllUsers(): List<User> {
        return userDAO.findAll()
    }

    fun addUser(user: User): Long {
        return userDAO.insert(user)
    }

    fun deleteUser(user: User) {
        userDAO.delete(user)
    }

    fun updateUser(user: User) {
        userDAO.update(user)
    }

}