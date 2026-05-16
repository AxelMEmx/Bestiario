package com.bestiario.hp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bestias")
data class Bestia(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val nombreLatin: String,
    val descripcion: String,
    val nivelPeligro: Int,
    val habitat: String,
    val habilidades: String,
    val primeraAparicion: String,
    val imagenUrl: String,
    val esFavorito: Boolean = false
)