package com.example.finalpam.ui.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

// Halaman Home Peserta
object DestinasiHomePstr : DestinasiNavigasi {
    override val route = "Peserta"
    override val titleRes = "Halaman Peserta"
}
