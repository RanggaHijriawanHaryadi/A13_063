package com.example.finalpam.entitas


import kotlinx.serialization.Serializable

@Serializable
data class Peserta(
    val id_peserta:Int = 0,
    val nama_peserta: String,
    val email: String,
    val nomor_telepon: String
)

