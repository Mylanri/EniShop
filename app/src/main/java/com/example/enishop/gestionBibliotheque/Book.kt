package com.example.enishop.gestionBibliotheque

class Book (title: String, author: String, year: Int, val genre: String) : Item(title, author, year), Borrowable {

    override fun borrow(user: User): Boolean {
        return if (isAvailable) {
            isAvailable = false
            user.borrowedItems.add(this)
            println("$title have been borrowed by ${user.name}.")
            true
        } else {
            println("$title is not available for borrowing.")
            false
        }
    }

    override fun returnItem(user: User) {
        if (user.borrowedItems.contains(this)) {
            isAvailable = true
            user.borrowedItems.remove(this)
            println("$title have been returned by ${user.name}.")
        }
    }
}
