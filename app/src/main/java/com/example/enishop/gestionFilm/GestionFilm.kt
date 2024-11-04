package com.example.enishop.gestionFilm

data class Film(val titre: String)

class ListeFilms {
    private val films = mutableMapOf<String, MutableList<Film>>(
        "Déjà vu" to mutableListOf(),
        "À voir" to mutableListOf()
    )

    fun ajouterFilmVus(titre: String) {
        if (!films["Déjà vu"]!!.any { it.titre.equals(titre, ignoreCase = true) }) {
            films["Déjà vu"]?.add(Film(titre))
            println("$titre a été ajouté à la liste des films déjà vus.")
        } else {
            println("$titre est déjà dans la liste des films déjà vus.")
        }
    }

    fun ajouterFilmAvoir(titre: String) {
        if (!films["À voir"]!!.any { it.titre.equals(titre, ignoreCase = true) }) {
            films["À voir"]?.add(Film(titre))
            println("$titre a été ajouté à la liste des films/séries à voir.")
        } else {
            println("$titre est déjà dans la liste des films/séries à voir.")
        }
    }

    fun afficherFilmsVus() {
        println("Films/Séries déjà vus :")
        films["Déjà vu"]?.forEach { film ->
            println("- ${film.titre}")
        }
    }

    fun afficherFilmsAvoir() {
        println("Films/Séries à voir :")
        films["À voir"]?.forEach { film ->
            println("- ${film.titre}")
        }
    }

    fun afficherTouteListe() {
        println("Liste complète :")
        afficherFilmsVus()
        afficherFilmsAvoir()
    }
}

fun main() {
    val listeFilms = ListeFilms()

    while (true) {
        println("\nMenu:")
        println("1. Ajouter un film déjà vu")
        println("2. Ajouter un film/série à voir")
        println("3. Voir les films/séries déjà vus")
        println("4. Voir toute la liste (déjà vus et à voir)")
        println("5. Quitter")

        print("Choisissez une option (1-5) : ")
        val choix = readLine()

        when (choix) {
            "1" -> {
                print("Entrez le nom du film déjà vu : ")
                val film = readLine()
                if (film != null) {
                    listeFilms.ajouterFilmVus(film)
                }
            }

            "2" -> {
                print("Entrez le nom du film/série à voir : ")
                val film = readLine()
                if (film != null) {
                    listeFilms.ajouterFilmAvoir(film)
                }
            }

            "3" -> {
                listeFilms.afficherFilmsVus()
            }

            "4" -> {
                listeFilms.afficherTouteListe()
            }

            "5" -> {
                println("Au revoir!")
                return
            }

            else -> {
                println("Choix invalide, veuillez réessayer.")
            }
        }
    }
}

