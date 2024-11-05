package com.example.enishop.gestionBibliotheque

interface Borrowable {
    fun borrow(user: User): Boolean
    fun returnItem(user: User)
}