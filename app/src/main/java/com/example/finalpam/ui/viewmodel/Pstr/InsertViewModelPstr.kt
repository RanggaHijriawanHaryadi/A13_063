package com.example.finalpam.ui.viewmodel.Pstr



data class InsertUiPesertaEvent(
    val id_peserta: Int ?= 0,
    val nama_peserta: String = "",
    val email: String = "",
    val nomor_telepon: String = "",
)