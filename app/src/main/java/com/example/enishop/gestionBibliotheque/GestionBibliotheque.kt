package com.example.enishop.gestionBibliotheque

fun main() {
    val kotlinProg = Book("Kotlin Programming", "Jane Doe", 2021, "Education")
    val flutterPro = Book("Flutter Programming", "Majid", 2023, "Education")
    val harryPotter = Book("Harry Potter", "JK Rowling", 1995, "Novel")

    val jeanMichel = User("Jean Michel", 1)
    val robert = User("Robert Jackson", 2)

    kotlinProg.borrow(jeanMichel)
    kotlinProg.borrow(robert)
    flutterPro.borrow(robert)

    kotlinProg.returnItem(jeanMichel)
    flutterPro.returnItem(robert)

    // Afficher les livres empruntés par chaque utilisateur
    // jeanMichel.listBorrowedBooks()
    // robert.listBorrowedBooks()
}