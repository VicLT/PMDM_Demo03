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

// Fragment que gestiona la visualización de una lista de items
class FragmentList : Fragment() {
    private lateinit var binding: ListFragmentBinding
    private val adapter = ItemsAdapter(onArchive =  { item ->
        sharedViewModel.archiveItem(item)
        updateAdapter()
    }, onShowInfo = { item ->
        showAlertDialog(item)
    })
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

    // Configurar el RecyclerView y cargar el adapter con la lista de elementos del ViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener la lista de items del ViewModel y enviarla al adapter
        adapter.submitList(sharedViewModel.fetchItems())
        binding.mRecycler.layoutManager = LinearLayoutManager(context)
        binding.mRecycler.adapter = adapter
    }

    // Actualizar la lista del adapter con los elementos más recientes del ViewModel
    private fun updateAdapter() {
        adapter.submitList(sharedViewModel.fetchItems())
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