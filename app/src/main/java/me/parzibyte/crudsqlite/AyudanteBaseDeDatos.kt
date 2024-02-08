package me.parzibyte.crudsqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AyudanteBaseDeDatos(context: Context?) :
    SQLiteOpenHelper(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS) {

    companion object {
        private const val NOMBRE_BASE_DE_DATOS = "mascotasDB"
        private const val NOMBRE_TABLA_MASCOTAS = "mascotas"
        private const val VERSION_BASE_DE_DATOS = 1
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            String.format(
                "CREATE TABLE IF NOT EXISTS %s(id integer primary key autoincrement, nombre text, edad int)",
                NOMBRE_TABLA_MASCOTAS
            )
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}