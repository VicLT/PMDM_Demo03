package edu.victorlamas.actdemo03.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.victorlamas.actdemo03.MainViewModel
import edu.victorlamas.actdemo03.databinding.ListFragmentBinding
import edu.victorlamas.actdemo03.model.Item
import edu.victorlamas.actdemo03.model.ItemsAdapter

// Fragment que gestiona la visualización de los elementos archivados
class FragmentArchive: Fragment() {
    private lateinit var binding: ListFragmentBinding
    private val adapter = ItemsAdapter (
        onArchive = {},
        onShowInfo = {item -> showAlertDialog(item)}
    )
    private val sharedViewModel: MainViewModel by activityViewModels()

    // Inflar el layout del fragment y devolver la vista para mostrar la interfaz
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el adapter del RecyclerView con los elementos archivados del ViewModel
        adapter.submitList(sharedViewModel.fetchArchiveItems())
        // Establece el layout manager para el RecyclerView
        binding.mRecycler.layoutManager = LinearLayoutManager(context)
        // Asocia el adapter al RecyclerView para mostrar los elementos archivados
        binding.mRecycler.adapter = adapter
    }

    // Mostrar un cuadro de diálogo con la descripción del item
    private fun showAlertDialog(item: Item) {
        MaterialAlertDialogBuilder(binding.root.context).apply {
            setTitle(item.title)
            setMessage(item.description)
            setPositiveButton(android.R.string.ok) { dialog, _ -> dialog.dismiss() }
        }.show()
    }
}