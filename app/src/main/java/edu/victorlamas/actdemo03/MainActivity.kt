package edu.victorlamas.actdemo03

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import edu.victorlamas.actdemo03.databinding.ActivityMainBinding
import edu.victorlamas.actdemo03.fragments.FragmentAdd
import edu.victorlamas.actdemo03.fragments.FragmentArchive
import edu.victorlamas.actdemo03.fragments.FragmentList

// Actividad principal que gestiona la interfaz de usuario y la navegación entre fragments
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Declaración de fragments utilizados en la actividad
    private lateinit var listFragment: FragmentList
    private lateinit var addFragment: FragmentAdd
    private lateinit var archiveFragment: FragmentArchive
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                0
            )
            insets
        }

        // Inicializar los fragments
        listFragment = FragmentList()
        addFragment = FragmentAdd()
        archiveFragment = FragmentArchive()

        // Cargar el fragment inicial basado en el estado del ViewModel
        if (mainViewModel.fragmentShowed == null) {
            loadFragment(listFragment) // Cargar el fragment de lista si no hay fragment mostrado
        } else {
            // Cargar el fragment correspondiente basado en el estado guardado
            when (mainViewModel.fragmentShowed) {
                listFragment.javaClass.simpleName -> loadFragment(listFragment)
                addFragment.javaClass.simpleName -> loadFragment(addFragment)
                archiveFragment.javaClass.simpleName -> loadFragment(archiveFragment)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        // Configurar el listener para el BottomNavigationView para cambiar entre fragments
        binding.mBottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.op_list -> loadFragment(listFragment)
                R.id.op_add -> loadFragment(addFragment)
                R.id.op_archive -> loadFragment(archiveFragment)
            }
            true // Indica que el evento se ha manejado
        }
    }

    // Cargar el fragment en el contenedor designado
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mFrameLayout.id, fragment)
            .commit()

        // Guardar el fragment que se está mostrando actualmente en el ViewModel
        mainViewModel.setFragmentShowed(fragment.javaClass.simpleName)
    }
}