package com.example.finalpam.ui.viewmodel.Pstr


import com.example.finalpam.entitas.Peserta




fun InsertUiPesertaEvent.toPstr(): Peserta = Peserta (
    id_peserta = id_peserta ?: 0,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon,
)


fun Peserta.toInsertUiPstrEvent(): InsertUiPesertaEvent = InsertUiPesertaEvent (
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon,
)

data class InsertUiPesertaEvent(
    val id_peserta: Int ?= 0,
    val nama_peserta: String = "",
    val email: String = "",
    val nomor_telepon: String = "",
)