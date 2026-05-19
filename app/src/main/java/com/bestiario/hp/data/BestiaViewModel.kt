package com.bestiario.hp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BestiaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BestiaRepository
    private val _queryBusqueda = MutableLiveData("")

    val todasLasBestias: LiveData<List<Bestia>>
    val bestiasFavoritas: LiveData<List<Bestia>>
    val resultadosBusqueda: LiveData<List<Bestia>>
    val bestiaAleatoria: LiveData<Bestia>

    init {
        val dao = BestiaDatabase.getDatabase(application).bestiaDao()
        repository = BestiaRepository(dao)

        todasLasBestias = repository.todasLasBestias
        bestiasFavoritas = repository.bestiasFavoritas
        bestiaAleatoria = repository.obtenerAleatoria()

        resultadosBusqueda = _queryBusqueda.switchMap { query ->
            repository.buscar(query)
        }
    }

    fun buscar(query: String) {
        _queryBusqueda.value = query
    }

    fun toggleFavorito(bestia: Bestia) {
        viewModelScope.launch {
            repository.toggleFavorito(bestia)
        }
    }

    fun obtenerBestiaPorId(id: Int): LiveData<Bestia> {
        return repository.obtenerPorId(id)
    }

    fun filtrarPorPeligro(nivel: Int): LiveData<List<Bestia>> {
        return repository.filtrarPorPeligro(nivel)
    }

    fun filtrarPorAparicion(aparicion: String): LiveData<List<Bestia>> {
        return repository.filtrarPorAparicion(aparicion)
    }

    fun filtrarLibros(): LiveData<List<Bestia>> = repository.filtrarLibros()
    fun filtrarPeliculas(): LiveData<List<Bestia>> = repository.filtrarPeliculas()

    val todasDesc: LiveData<List<Bestia>> = repository.todasDesc
}