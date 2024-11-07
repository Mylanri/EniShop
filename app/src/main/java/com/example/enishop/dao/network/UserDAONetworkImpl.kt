package com.example.enishop.dao.network

import com.example.enishop.bo.User
import com.example.enishop.dao.UserDAO

class UserDAONetworkImpl : UserDAO {

    private val usersInNetwork = mutableListOf<User>(
        User(
            id = 1,
            name = "admin",
            email = "admin",
            password = "admin"
        )
    )

    override fun findById(id: Long): User? {
        return usersInNetwork.find { it.id == id }
    }

    override fun insert(user: User): Long {
        usersInNetwork.add(user)
        return user.id
    }

    override fun findAll(): List<User> {
        return usersInNetwork
    }

    override fun delete(user: User) {
        usersInNetwork.remove(user)
    }

    override fun update(user: User) {
        val index = usersInNetwork.indexOfFirst { it.id == user.id }
        if (index != -1) {
            usersInNetwork[index] = user
        }
    }

    fun findByNameAndPassword(name: String, password: String): User? {
        return usersInNetwork.find { it.name == name && it.password == password }
    }
}