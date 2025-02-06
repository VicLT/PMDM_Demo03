package edu.victorlamas.actdemo03.model

data class Item(
    var id: Int = 0,
    val title: String,
    val description: String,
    var image: String = "",
    var archived: Boolean = false
) {
    companion object {
        var identifier: Int = 0
        val items: MutableList<Item> = mutableListOf()
    }

    // Sumar id y añadir imagen con los permisos en el manifest
    init {
        id = ++identifier
        image = "https://picsum.photos/200/200?image=$id"
    }
}

