package com.example.enishop.dao.memory

import com.example.enishop.bo.User
import com.example.enishop.dao.UserDAO

class UserDAOMemoryImpl : UserDAO {
    private val usersInMemory = mutableListOf<User>(
        User (
            id = 1,
            name = "admin",
            email = "admin",
            password = "admin"
        )
    )

    override fun findById(id: Long): User? {
        return usersInMemory.find { it.id == id }
    }

    override fun insert(user: User): Long {
        usersInMemory.add(user)
        return user.id // Suppose que l'ID est généré ailleurs dans ton modèle
    }

    override fun findAll(): List<User> {
        return usersInMemory
    }

    override fun delete(user: User) {
        usersInMemory.remove(user)
    }

    override fun update(user: User) {
        val index = usersInMemory.indexOfFirst { it.id == user.id }
        if (index != -1) {
            usersInMemory[index] = user
        }
    }

    fun findByNameAndPassword(name: String, password: String): User? {
        return usersInMemory.find { it.name == name && it.password == password }
    }
}