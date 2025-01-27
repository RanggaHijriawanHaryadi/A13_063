package com.example.finalpam.entitas

import kotlinx.serialization.Serializable

@Serializable
data class Tikets(
    val id_tiket:Int = 0,
    val id_event: Int = 0,
    val id_peserta:Int = 0,
    val kapasitas_tiket: Int ,
    val harga_tiket: String
)

@Serializable
data class TiketsResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tikets>
)

@Serializable
data class TiketsResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Tikets
)
