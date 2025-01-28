package com.example.finalpam.ui.viewmodel.Tkts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.Tikets
import com.example.finalpam.repository.EventRepository
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.repository.TiketsRepository
import kotlinx.coroutines.launch

class InsertViewModelTkts (
    private val tkts: TiketsRepository,
    private val peserta: PesertaRepository,
    private val event: EventRepository
): ViewModel() {

    var uiStateTkts by mutableStateOf(InsertUiTiketsState())
        private set

    // Dropdowm
    var pstrList by mutableStateOf<List<Peserta>>(emptyList())
    var evntList by mutableStateOf<List<Event>>(emptyList())

    init {
        getEvent()
    }
    init {
        getPeserta()
    }

    private fun getEvent(){
        viewModelScope.launch {
            try {
                evntList = event.getEvent().data
            }catch(e:Exception){

            }
        }
    }

    private fun getPeserta(){
        viewModelScope.launch {
            try {
                pstrList = peserta.getPeserta().data

            }catch(e:Exception){

            }
        }
    }

    fun updateInsertTktsState(insertUiTiketsEvent: InsertUiTiketsEvent) {
        uiStateTkts = InsertUiTiketsState(insertUiTiketsEvent = insertUiTiketsEvent)
    }
    suspend fun insertTkts(){
        viewModelScope.launch {
            try {
                tkts.insertTikets(uiStateTkts.insertUiTiketsEvent.toTkts())
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiTiketsState(
    val insertUiTiketsEvent: InsertUiTiketsEvent = InsertUiTiketsEvent(),
    )

fun Tikets.toUiStateTkts(): InsertUiTiketsState = InsertUiTiketsState(
    insertUiTiketsEvent = toInsertUiTktsEvent()
)
fun InsertUiTiketsEvent.toTkts(): Tikets = Tikets (
    id_tiket = id_tiket ?: 0,
    id_event = id_event ?:0,
    id_peserta = id_peserta ?:0,
    kapasitas_tiket = kapasitas_tiket ?:0,
    harga_tiket = harga_tiket,
)


fun Tikets.toInsertUiTktsEvent(): InsertUiTiketsEvent = InsertUiTiketsEvent (
    id_tiket = id_tiket,
    id_event = id_event,
    id_peserta = id_peserta,
    kapasitas_tiket = kapasitas_tiket,
    harga_tiket = harga_tiket
)

data class InsertUiTiketsEvent(
    val id_tiket: Int ?= 0,
    val id_event: Int ?= 0,
    val id_peserta: Int ?= 0,
    val kapasitas_tiket: Int ?=0,
    val harga_tiket: String = "",
)