package com.example.enishop.gestionBibliotheque

class User(val name: String, val id: Int, val borrowedItems: MutableList<Item> = mutableListOf()) {

    fun borrow(item: Item): Boolean {
        return if (item.isAvailable) {
            item.isAvailable = false
            borrowedItems.add(item)
            println("${item.title} have been borrowed by $name.")
            true
        } else {
            println("${item.title} is not available. Please try again later.")
            false
        }
    }

    fun returnItem(item: Item) {
        if (borrowedItems.contains(item)) {
            item.isAvailable = true
            borrowedItems.remove(item)
            println("${item.title} has been returned by $name.")
        }
    }
}