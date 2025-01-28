package com.example.finalpam.ui.viewmodel.Tski

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.Tikets
import com.example.finalpam.entitas.Transaksi
import com.example.finalpam.repository.EventRepository
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.repository.TiketsRepository
import com.example.finalpam.repository.TransaksiRepository
import kotlinx.coroutines.launch

class InsertViewModelTski(
    private val tski : TransaksiRepository,
    private val tiket: TiketsRepository,
    private val peserta: PesertaRepository,
    private val event: EventRepository
): ViewModel() {
    var uiStateTski by mutableStateOf(InsertUiTransaksiState())
        private set

    // list tiket
    var tktsList by mutableStateOf<List<Tikets>>(emptyList())
    init {
        getTikets()
    }
    private fun getTikets(){
        viewModelScope.launch {
            try {
                tktsList = tiket.getTikets().data

            }catch(e:Exception){

            }
        }
    }
    //list peserta
    var pstrList by mutableStateOf<List<Peserta>>(emptyList())
    init {
        getPeserta()
    }
    private fun getPeserta(){
        viewModelScope.launch {
            try {
                pstrList = peserta.getPeserta().data

            }catch(e:Exception){

            }
        }
    }
    //list event
    var evntList by mutableStateOf<List<Event>>(emptyList())
    init {
        getEvent()
    }
    private fun getEvent(){
        viewModelScope.launch {
            try {
                evntList = event.getEvent().data

            }catch(e:Exception){

            }
        }
    }

    fun updateInsertTskiState(insertUiTransaksiEvent: InsertUiTransaksiEvent) {
        uiStateTski = InsertUiTransaksiState(insertUiTransaksiEvent = insertUiTransaksiEvent)
    }

    suspend fun insertTski(){
        viewModelScope.launch {
            try {
                tski.insertTransaksi(uiStateTski.insertUiTransaksiEvent.toTski())
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiTransaksiState(
    val insertUiTransaksiEvent: InsertUiTransaksiEvent = InsertUiTransaksiEvent(),
)

fun InsertUiTransaksiEvent.toTski(): Transaksi = Transaksi (
    id_transaksi = id_transaksi ?: 0,
    id_tiket = id_tiket ?:0,
    id_event = id_event ?:0,
    id_peserta = id_peserta ?:0,
    jumlah_tiket = jumlah_tiket,
    jumlah_pembayaran = jumlah_pembayaran,
    tanggal_transaksi = tanggal_transaksi,
)


fun Transaksi.toUiStateTski(): InsertUiTransaksiState = InsertUiTransaksiState(
    insertUiTransaksiEvent = toInsertUiTskiEvent()
)
fun Transaksi.toInsertUiTskiEvent(): InsertUiTransaksiEvent = InsertUiTransaksiEvent (
    id_transaksi = id_transaksi,
    id_tiket = id_tiket,
    id_event = id_event,
    id_peserta = id_peserta,
    jumlah_tiket = jumlah_tiket,
    jumlah_pembayaran = jumlah_pembayaran,
    tanggal_transaksi = tanggal_transaksi
)

data class InsertUiTransaksiEvent(
    val id_transaksi: Int ?= 0,
    val id_tiket: Int ?= 0 ,
    val id_event: Int ?=0,
    val id_peserta: Int ?=0,
    val jumlah_tiket: String = "",
    val jumlah_pembayaran: String = "",
    val tanggal_transaksi: String = "",
)