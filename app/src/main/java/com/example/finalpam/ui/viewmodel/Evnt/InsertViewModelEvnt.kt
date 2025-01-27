package com.example.finalpam.ui.viewmodel.Evnt



data class InsertUiEvntEvent(
    val id_event: Int ?= 0,
    val nama_event: String = "",
    val deskripsi_event: String = "",
    val tanggal_event: String = "",
    val lokasi_event: String = ""
)