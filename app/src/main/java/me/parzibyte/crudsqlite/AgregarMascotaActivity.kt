package me.parzibyte.crudsqlite

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import me.parzibyte.crudsqlite.controllers.MascotasController
import me.parzibyte.crudsqlite.databinding.ActivityAgregarMascotaBinding
import me.parzibyte.crudsqlite.databinding.ActivityEditarMascotaBinding
import me.parzibyte.crudsqlite.modelos.Mascota

class AgregarMascotaActivity : AppCompatActivity() {
    // private lateinit var btnAgregarMascota: Button
    // private lateinit var btnCancelarNuevaMascota: Button
    // private lateinit var etNombre: EditText
    // private lateinit var etEdad: EditText
    private lateinit var mascotasController: MascotasController

    private lateinit var binding: ActivityAgregarMascotaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_agregar_mascota)
        binding = ActivityAgregarMascotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Instanciar vistas
        // etNombre = findViewById(R.id.etNombre)
        // etEdad = findViewById(R.id.etEdad)
        // btnAgregarMascota = findViewById(R.id.btnAgregarMascota)
        // btnCancelarNuevaMascota = findViewById(R.id.btnCancelarNuevaMascota)
        // Crear el controlador
        mascotasController = MascotasController(this@AgregarMascotaActivity)

        // Agregar listener del botón de guardar
        binding.btnAgregarMascota.setOnClickListener(View.OnClickListener {
            // Resetear errores a ambos
            binding.etNombre.setError(null)
            binding.etEdad.setError(null)
            val nombre = binding.etNombre.getText().toString()
            val edadComoCadena = binding.etEdad.getText().toString()
            if (nombre.isEmpty()) {
                binding.etNombre.setError("Escribe el nombre de la mascota")
                binding.etNombre.requestFocus()

                return@OnClickListener
            }
            if ("" == edadComoCadena) {
                binding.etEdad.setError("Escribe la edad de la mascota")
                binding.etEdad.requestFocus()

                return@OnClickListener
            }

            // Ver si es un entero
            val edad: Int
            edad = try {
                binding.etEdad.getText().toString().toInt()
            } catch (e: NumberFormatException) {
                binding.etEdad.setError("Escribe un número")
                binding.etEdad.requestFocus()

                return@OnClickListener
            }
            // Ya pasó la validación
            val nuevaMascota = Mascota(nombre, edad)
            val id = mascotasController.nuevaMascota(nuevaMascota)
            if (id == -1L) {
                // De alguna manera ocurrió un error
                Toast.makeText(
                    this@AgregarMascotaActivity,
                    "Error al guardar. Intenta de nuevo",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Terminar
                finish()
            }
        })

        // El de cancelar simplemente cierra la actividad
        binding.btnCancelarNuevaMascota.setOnClickListener(View.OnClickListener { finish() })
    }
}