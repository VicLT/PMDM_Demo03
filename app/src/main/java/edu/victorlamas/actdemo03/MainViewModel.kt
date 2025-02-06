package edu.victorlamas.actdemo03

import android.util.Log
import androidx.lifecycle.ViewModel
import edu.victorlamas.actdemo03.model.Item

// ViewModel para gestionar la persistencia y lógica de negocio de la aplicación
class MainViewModel: ViewModel() {
    // Almacenar el nombre del fragment actualmente mostrado
    private var _fragmentShowed: String? = null
    val fragmentShowed: String?
        get() = _fragmentShowed // Getter para acceder al fragmento mostrado

    // Establecer el fragmento que se está mostrando actualmente
    fun setFragmentShowed(fragmentShowed: String) {
        _fragmentShowed = fragmentShowed
    }

    // Añadir un nuevo item a la lista
    fun addItem(item: Item) {
        Item.items.add(item)
        Log.i("MainViewModel", "addItem: $item")
    }

    // Archivar un item de la lista
    fun archiveItem(item: Item) {
        Item.items.firstOrNull{item.id == it.id}?.archived = true
        Log.i("MainViewModel", "archiveItem: $item")
    }

    // Obtener una lista de items que no estén archivados
    fun fetchItems(): MutableList<Item> {
        Log.i("MainViewModel",
            "fetchItems: ${Item.items.filter { !it.archived }.size}")
        return Item.items.filter { !it.archived }.toMutableList()
    }

    // Obtener una lista de items archivados
    fun fetchArchiveItems(): MutableList<Item> {
        Log.i("MainViewModel",
            "fetchArchiveItems: ${Item.items.filter { it.archived }.size}")
        return Item.items.filter { it.archived }.toMutableList()
    }
}