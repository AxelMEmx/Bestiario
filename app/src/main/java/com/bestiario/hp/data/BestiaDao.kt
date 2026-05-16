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
}