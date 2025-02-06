package edu.victorlamas.actdemo03.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import edu.victorlamas.actdemo03.databinding.ItemsBinding

// Adapter para gestionar la visualización de elementos en un RecyclerView
class ItemsAdapter(
    val onArchive: (Item) -> Unit, // Callback que se invoca al archivar un item
    val onShowInfo: (Item) -> Unit
) : ListAdapter<Item, ItemsAdapter.ViewHolder>(ItemsDiffCallback()) {

    // Crear y devolver un ViewHolder para una nueva vista de item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    // Asociar los datos del item a la vista del ViewHolder en la posición especificada
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // Representar cada item en el RecyclerView
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemsBinding.bind(itemView)

        // Vincular los datos del item a la vista correspondiente
        fun bind(item: Item) {
            binding.tvId.text = item.id.toString()
            binding.tvTitle.text = item.title

            // Cargar la imagen del item en la vista
            Glide.with(itemView)
                .load(item.image)
                .fitCenter()
                .transform(RoundedCorners(16))
                .into(binding.ivItem)

            // Si el item está archivado, ocultar botón de archivar
            if (item.archived) {
                binding.btnArchive.visibility = View.GONE
            } else {
                binding.btnArchive.setOnClickListener {
                    onArchive(item)
                }
            }

            // Listener de click en el item
            binding.root.setOnClickListener {
                onShowInfo(item)
            }
        }
    }

    // Comparar los items para saber si son iguales
    class ItemsDiffCallback : DiffUtil.ItemCallback<Item>() {
        // Comprobar si los items son los mismos basándose en el ID
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }
        // Comprobar si los contenidos de los items son iguales
        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
}