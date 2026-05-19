package com.bestiario.hp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BestiaDao {

    @Query("SELECT * FROM bestias ORDER BY nombre ASC")
    fun obtenerTodas(): LiveData<List<Bestia>>

    @Query("SELECT * FROM bestias WHERE id = :id")
    fun obtenerPorId(id: Int): LiveData<Bestia>

    @Query("SELECT * FROM bestias WHERE nombre LIKE '%' || :busqueda || '%'")
    fun buscar(busqueda: String): LiveData<List<Bestia>>

    @Query("SELECT * FROM bestias WHERE esFavorito = 1")
    fun obtenerFavoritas(): LiveData<List<Bestia>>

    @Query("SELECT * FROM bestias ORDER BY RANDOM() LIMIT 1")
    fun obtenerAleatoria(): LiveData<Bestia>

    @Update
    suspend fun actualizar(bestia: Bestia)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodas(bestias: List<Bestia>)

    @Query("SELECT * FROM bestias WHERE nivelPeligro = :nivel ORDER BY nombre ASC")
    fun filtrarPorPeligro(nivel: Int): LiveData<List<Bestia>>

    @Query("SELECT * FROM bestias WHERE primeraAparicion LIKE '%' || :aparicion || '%' ORDER BY nombre ASC")
    fun filtrarPorAparicion(aparicion: String): LiveData<List<Bestia>>

    @Query("""SELECT * FROM bestias WHERE 
    primeraAparicion LIKE '%Piedra%' OR 
    primeraAparicion LIKE '%Cámara%' OR 
    primeraAparicion LIKE '%Prisionero%' OR 
    primeraAparicion LIKE '%Cáliz%' OR 
    primeraAparicion LIKE '%Orden%' OR 
    primeraAparicion LIKE '%Príncipe%' OR
    primeraAparicion LIKE '%Reliquias%' OR 
    primeraAparicion LIKE '%Fantásticos y%' OR
    primeraAparicion LIKE '%edición 2017%' OR
    primeraAparicion LIKE '%Quidditch%'
    ORDER BY nombre ASC""")
    fun filtrarLibros(): LiveData<List<Bestia>>

    @Query("""SELECT * FROM bestias WHERE 
    primeraAparicion LIKE '%Frank%' OR 
    primeraAparicion LIKE '%Crímenes%' OR
    primeraAparicion LIKE '%Secretos de Dumbledore%'
    ORDER BY nombre ASC""")
    fun filtrarPeliculas(): LiveData<List<Bestia>>

    @Query("SELECT * FROM bestias ORDER BY nombre DESC")
    fun obtenerTodasDesc(): LiveData<List<Bestia>>
}