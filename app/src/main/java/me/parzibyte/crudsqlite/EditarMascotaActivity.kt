package me.parzibyte.crudsqlite

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import me.parzibyte.crudsqlite.controllers.MascotasController
import me.parzibyte.crudsqlite.databinding.ActivityEditarMascotaBinding
import me.parzibyte.crudsqlite.databinding.ActivityMainBinding
import me.parzibyte.crudsqlite.modelos.Mascota

class EditarMascotaActivity : AppCompatActivity() {
    private lateinit var mascota: Mascota //La mascota que vamos a estar editando
    private lateinit var mascotasController: MascotasController

    // private lateinit var etEditarNombre: EditText
    // private lateinit var etEditarEdad: EditText
    // private lateinit var btnGuardarCambios: Button
    // private lateinit var btnCancelarEdicion: Button

    private lateinit var binding: ActivityEditarMascotaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_editar_mascota)
        binding = ActivityEditarMascotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar datos que enviaron
        val extras = intent.extras
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish()
            return
        }
        // Instanciar el controlador de las mascotas
        mascotasController = MascotasController(this@EditarMascotaActivity)

        // Rearmar la mascota
        // Nota: igualmente solamente podríamos mandar el id y recuperar la mascota de la BD
        val idMascota = extras.getLong("idMascota")
        val nombreMascota = extras.getString("nombreMascota")
        val edadMascota = extras.getInt("edadMascota")
        mascota = Mascota(nombreMascota, edadMascota, idMascota)


        // Ahora declaramos las vistas
        // etEditarEdad = findViewById(R.id.etEditarEdad)
        // etEditarNombre = findViewById(R.id.etEditarNombre)
        // btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionMascota)
        // btnGuardarCambios = findViewById(R.id.btnGuardarCambiosMascota)


        // Rellenar los EditText con los datos de la mascota
        binding.etEditarEdad.setText(mascota.edad.toString())
        binding.etEditarNombre.setText(mascota.nombre)

        // Listener del click del botón para salir, simplemente cierra la actividad
        binding.btnCancelarEdicionMascota.setOnClickListener(View.OnClickListener { finish() })

        // Listener del click del botón que guarda cambios
        binding.btnGuardarCambiosMascota.setOnClickListener(View.OnClickListener {
            // Remover previos errores si existen
            binding.etEditarNombre.setError(null)
            binding.etEditarEdad.setError(null)
            // Crear la mascota con los nuevos cambios pero ponerle
            // el id de la anterior
            val nuevoNombre = binding.etEditarNombre.getText().toString()
            val posibleNuevaEdad = binding.etEditarEdad.getText().toString()
            if (nuevoNombre.isEmpty()) {
                binding.etEditarNombre.setError("Escribe el nombre")
                binding.etEditarNombre.requestFocus()

                return@OnClickListener
            }
            if (posibleNuevaEdad.isEmpty()) {
                binding.etEditarEdad.setError("Escribe la edad")
                binding.etEditarEdad.requestFocus()

                return@OnClickListener
            }
            // Si no es entero, igualmente marcar error
            val nuevaEdad: Int
            nuevaEdad = try {
                posibleNuevaEdad.toInt()
            } catch (e: NumberFormatException) {
                binding.etEditarEdad.setError("Escribe un número")
                binding.etEditarEdad.requestFocus()
                return@OnClickListener
            }
            // Si llegamos hasta aquí es porque los datos ya están validados
            val mascotaConNuevosCambios = Mascota(nuevoNombre, nuevaEdad, mascota.id)
            val filasModificadas = mascotasController.guardarCambios(mascotaConNuevosCambios)
            if (filasModificadas != 1) {
                // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                Toast.makeText(
                    this@EditarMascotaActivity,
                    "Error guardando cambios. Intente de nuevo.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Si las cosas van bien, volvemos a la principal
                // cerrando esta actividad
                finish()
            }
        })
    }
}