package edu.victorlamas.actdemo03.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import edu.victorlamas.actdemo03.MainViewModel
import edu.victorlamas.actdemo03.R
import edu.victorlamas.actdemo03.databinding.AddFragmentBinding
import edu.victorlamas.actdemo03.model.Item

// Gestionar la interfaz para añadir nuevos elementos a la lista
class FragmentAdd : Fragment() {
    private lateinit var binding: AddFragmentBinding
    private val sharedViewModel: MainViewModel by activityViewModels()

    // Inflar el layout del fragment y devolver la vista para mostrar la interfaz
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Configurar el comportamiento de la vista después de ser creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Establecer un listener para el botón de añadir
        binding.btnSave.setOnClickListener {
            binding.tilTitle.error = null
            binding.tilDescription.error = null

            val title = binding.tietTitle.text.toString().trim()
            val description = binding.tietDescription.text.toString().trim()

            if (title.isEmpty()) {
                binding.tilTitle.error = getString(R.string.error_title)
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                binding.tilDescription.error = getString(R.string.error_description)
                return@setOnClickListener
            }

            // Guardar un nuevo elemento en el ViewModel
            sharedViewModel.addItem(Item(title = title, description =  description))

            // Limpiar los campos de texto después de añadir el elemento
            binding.tietTitle.text?.clear()
            binding.tietDescription.text?.clear()

            // Oculta el teclado virtual al enviar el formulario
            val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE)
                    as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}