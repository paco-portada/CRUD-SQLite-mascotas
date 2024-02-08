package me.parzibyte.crudsqlite.controllers

import android.content.ContentValues
import android.content.Context
import me.parzibyte.crudsqlite.AyudanteBaseDeDatos
import me.parzibyte.crudsqlite.modelos.Mascota

class MascotasController(contexto: Context?) {
    private val ayudanteBaseDeDatos: AyudanteBaseDeDatos
    private val NOMBRE_TABLA = "mascotas"

    init {
        ayudanteBaseDeDatos = AyudanteBaseDeDatos(contexto)
    }

    fun eliminarMascota(mascota: Mascota): Int {
        val baseDeDatos = ayudanteBaseDeDatos.writableDatabase
        val argumentos = arrayOf(mascota.id.toString())
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos)
    }

    fun nuevaMascota(mascota: Mascota): Long {
        // writable porque vamos a insertar
        val baseDeDatos = ayudanteBaseDeDatos.writableDatabase
        val valoresParaInsertar = ContentValues()
        valoresParaInsertar.put("nombre", mascota.nombre)
        valoresParaInsertar.put("edad", mascota.edad)
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar)
    }

    fun guardarCambios(mascotaEditada: Mascota): Int {
        val baseDeDatos = ayudanteBaseDeDatos.writableDatabase
        val valoresParaActualizar = ContentValues()
        valoresParaActualizar.put("nombre", mascotaEditada.nombre)
        valoresParaActualizar.put("edad", mascotaEditada.edad)
        // where id...
        val campoParaActualizar = "id = ?"
        // ... = idMascota
        val argumentosParaActualizar = arrayOf(mascotaEditada.id.toString())
        return baseDeDatos.update(
            NOMBRE_TABLA,
            valoresParaActualizar,
            campoParaActualizar,
            argumentosParaActualizar
        )
    }

    fun obtenerMascotas(): ArrayList<Mascota> {
        val mascotas = ArrayList<Mascota>()
        // readable porque no vamos a modificar, solamente leer
        val baseDeDatos = ayudanteBaseDeDatos.readableDatabase
        // SELECT nombre, edad, id
        val columnasAConsultar = arrayOf("nombre", "edad", "id")
        val cursor = baseDeDatos.query(
            NOMBRE_TABLA,  //from mascotas
            columnasAConsultar,
            null,
            null,
            null,
            null,
            null
        )
            ?: /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return mascotas
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return mascotas

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de mascotas
        do {
            // El 0 es el número de la columna, como seleccionamos
            // nombre, edad,id entonces el nombre es 0, edad 1 e id es 2
            val nombreObtenidoDeBD = cursor.getString(0)
            val edadObtenidaDeBD = cursor.getInt(1)
            val idMascota = cursor.getLong(2)
            val mascotaObtenidaDeBD = Mascota(nombreObtenidoDeBD, edadObtenidaDeBD, idMascota)
            mascotas.add(mascotaObtenidaDeBD)
        } while (cursor.moveToNext())

        // Fin del ciclo. Cerramos cursor y regresamos la lista de mascotas :)
        cursor.close()
        return mascotas
    }
}