package com.example.finalpam.ui.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

// Halaman Utama
object DestinasiHalamanUtama: DestinasiNavigasi{
    override val route = "Utama"
    override val titleRes = "Halaman Utama"
}

// Halaman Home Peserta
object DestinasiHomePstr : DestinasiNavigasi {
    override val route = "Peserta"
    override val titleRes = "Halaman Peserta"
}
object DestinasiInsertPstr: DestinasiNavigasi{
    override val route= "Insert Peserta"
    override val titleRes = "Tambah data Peserta"
}
object DestinasiDetailPstr : DestinasiNavigasi {
    override val route= "detail peserta"
    override val titleRes = "Detail Peserta"
    const val Id_Peserta = "id"
    val routeWithArgs = "$route/{$Id_Peserta}"
}
object DestinasiUpdatePstr: DestinasiNavigasi {
    override val route = "update peserta"
    override val titleRes = "Update Peserta"
    const val Id_Peserta = "id"
    val routesWithArg = "$route/{$Id_Peserta}"
}