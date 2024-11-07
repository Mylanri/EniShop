package com.example.enishop.dao

import com.example.enishop.bo.User

interface UserDAO {
    fun findById(id: Long): User?
    fun insert(user: User): Long
    fun findAll(): List<User>
    fun delete(user: User)
    fun update(user: User)
}