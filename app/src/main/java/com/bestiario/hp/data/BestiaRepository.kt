package com.bestiario.hp.data

import androidx.lifecycle.LiveData

class BestiaRepository(private val dao: BestiaDao) {

    val todasLasBestias: LiveData<List<Bestia>> = dao.obtenerTodas()
    val bestiasFavoritas: LiveData<List<Bestia>> = dao.obtenerFavoritas()

    fun buscar(query: String): LiveData<List<Bestia>> = dao.buscar(query)

    fun obtenerPorId(id: Int): LiveData<Bestia> = dao.obtenerPorId(id)

    fun obtenerAleatoria(): LiveData<Bestia> = dao.obtenerAleatoria()

    suspend fun toggleFavorito(bestia: Bestia) {
        dao.actualizar(bestia.copy(esFavorito = !bestia.esFavorito))
    }
}