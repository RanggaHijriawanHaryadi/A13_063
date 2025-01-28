package com.example.finalpam.ui.viewmodel.Tski

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.Tikets
import com.example.finalpam.repository.EventRepository
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.repository.TiketsRepository
import com.example.finalpam.repository.TransaksiRepository
import com.example.finalpam.ui.navigation.DestinasiUpdateTkts
import com.example.finalpam.ui.navigation.DestinasiUpdateTski

import kotlinx.coroutines.launch

class UpdateViewModelTski(
    savedStateHandle: SavedStateHandle,
    private val transaksiRepository: TransaksiRepository,
    private val tiket: TiketsRepository,
    private val peserta: PesertaRepository,
    private val event: EventRepository
):ViewModel() {
    var updateUiTskiState by mutableStateOf(InsertUiTransaksiState())
        private set
    //list tiket
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
    // list event
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
    // list peserta
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

    private val id_transaksi: Int = checkNotNull(savedStateHandle[DestinasiUpdateTski.Id_Transaksi])

    init {
        viewModelScope.launch {
            updateUiTskiState = transaksiRepository.getTransaksiById(id_transaksi).data
                .toUiStateTski()
        }
    }

    fun updateTskiState(insertUiTransaksiEvent: InsertUiTransaksiEvent){
        updateUiTskiState = InsertUiTransaksiState(insertUiTransaksiEvent = insertUiTransaksiEvent)
    }

    suspend fun updateTski(){
        viewModelScope.launch {
            try {
                transaksiRepository.updateTransaksi(id_transaksi, updateUiTskiState.insertUiTransaksiEvent.toTski())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}