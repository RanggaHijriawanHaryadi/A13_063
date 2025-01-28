package com.example.finalpam.entitas


import kotlinx.serialization.Serializable

@Serializable
data class Transaksi(
    val id_transaksi:Int = 0,
    val id_tiket: Int = 0,
    val id_event: Int = 0,
    val id_peserta: Int = 0,
    val jumlah_tiket:String,
    val jumlah_pembayaran: String,
    val tanggal_transaksi: String
)

@Serializable
data class TransaksiResponse(
    val status: Boolean,
    val message: String,
    val data: List<Transaksi>
)

@Serializable
data class TransaksiResponseDetail(
    val status: Boolean,
    val message: String,
    val data: Transaksi
)