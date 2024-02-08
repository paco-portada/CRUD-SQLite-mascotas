package me.parzibyte.crudsqlite

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.OnLongClickListener
import me.parzibyte.crudsqlite.RecyclerTouchListener.ClickListener
import me.parzibyte.crudsqlite.controllers.MascotasController
import me.parzibyte.crudsqlite.databinding.ActivityMainBinding
import me.parzibyte.crudsqlite.modelos.Mascota

class MainActivity : AppCompatActivity() {
    private lateinit var listaDeMascotas: List<Mascota>
    private lateinit var adaptadorMascotas: AdaptadorMascotas
    private lateinit var mascotasController: MascotasController
    // private lateinit var recyclerView: RecyclerView
    // private lateinit var fabAgregarMascota: FloatingActionButton

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        // Ojo: este código es generado automáticamente, pone la vista y ya, pero
        // no tiene nada que ver con el código que vamos a escribir
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lo siguiente sí es nuestro ;)
        // Definir nuestro controlador
        mascotasController = MascotasController(this@MainActivity)

        // Instanciar vistas
        // recyclerView = findViewById(R.id.recyclerViewMascotas)
        // fabAgregarMascota = findViewById(R.id.fabAgregarMascota)


        // Por defecto es una lista vacía,
        // se la ponemos al adaptador y configuramos el recyclerView
        listaDeMascotas = ArrayList()
        adaptadorMascotas = AdaptadorMascotas(listaDeMascotas)
        binding.recyclerViewMascotas.setLayoutManager(LinearLayoutManager(applicationContext))
        binding.recyclerViewMascotas.setItemAnimator(DefaultItemAnimator())
        binding.recyclerViewMascotas.setAdapter(adaptadorMascotas)

        // Una vez que ya configuramos el RecyclerView le ponemos los datos de la BD
        refrescarListaDeMascotas()

        // Listener de los clicks en la lista, o sea el RecyclerView
        binding.recyclerViewMascotas.addOnItemTouchListener(
            RecyclerTouchListener(
                applicationContext,
                binding.recyclerViewMascotas,
                object : ClickListener {
                    // Un toque sencillo
                    override fun onClick(view: View?, position: Int) {
                        // Pasar a la actividad EditarMascotaActivity.java
                        val mascotaSeleccionada = listaDeMascotas.get(position)
                        val intent = Intent(this@MainActivity, EditarMascotaActivity::class.java)

                        intent.putExtra("idMascota", mascotaSeleccionada.id)
                        intent.putExtra("nombreMascota", mascotaSeleccionada.nombre)
                        intent.putExtra("edadMascota", mascotaSeleccionada.edad)
                        startActivity(intent)
                    }

                    // Un toque largo
                    override fun onLongClick(view: View?, position: Int) {
                        val mascotaParaEliminar = listaDeMascotas.get(position)
                        val dialog = AlertDialog.Builder(this@MainActivity)
                            .setPositiveButton("Sí, eliminar") { dialog, which ->
                                mascotasController!!.eliminarMascota(mascotaParaEliminar)
                                refrescarListaDeMascotas()
                            }
                            .setNegativeButton("Cancelar") { dialog, which -> dialog.dismiss() }
                            .setTitle("Confirmar")
                            .setMessage("¿Eliminar a la mascota " + mascotaParaEliminar.nombre + "?")
                            .create()
                        dialog.show()
                    }
                })
        )

        // Listener del FAB
        binding.fabAgregarMascota.setOnClickListener(View.OnClickListener { // Simplemente cambiamos de actividad
            val intent = Intent(this@MainActivity, AgregarMascotaActivity::class.java)
            startActivity(intent)
        })

        // Créditos
        binding.fabAgregarMascota.setOnLongClickListener(OnLongClickListener {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Acerca de")
                .setMessage("CRUD de Android con SQLite creado por parzibyte [parzibyte.me]\n\nIcons made by Freepik from www.flaticon.com ")
                .setNegativeButton("Cerrar") { dialogo, which -> dialogo.dismiss() }
                .setPositiveButton("Sitio web") { dialog, which ->
                    val intentNavegador =
                        Intent(Intent.ACTION_VIEW, Uri.parse("https://parzibyte.me"))
                    startActivity(intentNavegador)
                }
                .create()
                .show()
            false
        })
    }

    override fun onResume() {
        super.onResume()
        refrescarListaDeMascotas()
    }

    fun refrescarListaDeMascotas() {
        /*
         * ==========
         * Justo aquí obtenemos la lista de la BD
         * y se la ponemos al RecyclerView
         * ============
         *
         * */
        if (adaptadorMascotas == null) return
        listaDeMascotas = mascotasController.obtenerMascotas()
        adaptadorMascotas.setListaDeMascotas(listaDeMascotas)
        adaptadorMascotas.notifyDataSetChanged()
    }
}