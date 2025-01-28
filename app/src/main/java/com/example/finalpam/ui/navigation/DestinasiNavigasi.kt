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

// Halaman Home Event
object DestinasiHomeEvnt: DestinasiNavigasi {
    override val route = "Event"
    override val titleRes = "Halaman Event"
}
object DestinasiInsertEvnt: DestinasiNavigasi{
    override val route = "Insert event"
    override val titleRes = "Tambah data Event"
}
object DestinasiDetailEvnt: DestinasiNavigasi {
    override val route= "detail event"
    override val titleRes = "Detail Event"
    const val Id_Event = "id"
    val routeWithArgs = "$route/{$Id_Event}"
}
object DestinasiUpdateEvnt: DestinasiNavigasi {
    override val route = "update event"
    override val titleRes = "Update Event"
    const val Id_Event = "id"
    val routesWithArg = "$route/{$Id_Event}"
}

// Halaman Home Tikets
object DestinasiHomeTkts: DestinasiNavigasi{
    override val route = "Tikets"
    override val titleRes = "Halaman Tikets"
}
object DestinasiInsertTkts: DestinasiNavigasi{
    override val route = "Insert tikets"
    override val titleRes = "Tambah data Tiket"
}
object DestinasiDetailTkts: DestinasiNavigasi {
    override val route= "detail Tiket"
    override val titleRes = "Detail Tiket"
    const val Id_Tiket = "id"
    val routeWithArgs = "$route/{$Id_Tiket}"
}
object DestinasiUpdateTkts: DestinasiNavigasi {
    override val route = "update Tiket"
    override val titleRes = "Update Tiket"
    const val Id_Tiket = "id"
    val routesWithArg = "$route/{$Id_Tiket}"
}

// Halaman Home Transaksi
object  DestinasiHomeTski: DestinasiNavigasi{
    override val route = "Transaksi"
    override val titleRes = "Halaman Transaksi"
}
object DestinasiInsertTski: DestinasiNavigasi{
    override val route = "Insert transaksi"
    override val titleRes = "Tambah data Transaksi"
}
object DestinasiDetailTski: DestinasiNavigasi{
    override val route= "detail Transaksi"
    override val titleRes = "Detail Transaksi"
    const val Id_Transaksi = "id"
    val routeWithArgs = "$route/{$Id_Transaksi}"
}
object DestinasiUpdateTski: DestinasiNavigasi {
    override val route = "update Transaksi"
    override val titleRes = "Update Transaksi"
    const val Id_Transaksi = "id"
    val routesWithArg = "$route/{$Id_Transaksi}"
}
