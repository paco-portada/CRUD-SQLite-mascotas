package me.parzibyte.crudsqlite.modelos

class Mascota {
    @JvmField
    var nombre: String
    @JvmField
    var edad: Int
    @JvmField
    var id: Long = 0 // El ID de la BD

    constructor(nombre: String, edad: Int) {
        this.nombre = nombre
        this.edad = edad
    }

    // Constructor para cuando instanciamos desde la BD
    constructor(nombre: String, edad: Int, id: Long) {
        this.nombre = nombre
        this.edad = edad
        this.id = id
    }

    override fun toString(): String {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                '}'
    }
}