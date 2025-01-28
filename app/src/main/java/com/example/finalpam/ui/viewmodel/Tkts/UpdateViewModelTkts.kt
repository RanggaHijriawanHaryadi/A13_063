package com.example.finalpam.ui.viewmodel.Tkts

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
import com.example.finalpam.ui.navigation.DestinasiUpdateTkts
import kotlinx.coroutines.launch

class UpdateViewModelTkts(
    savedStateHandle: SavedStateHandle,
    private val tiketsRepository: TiketsRepository,
    private val peserta: PesertaRepository,
    private val event: EventRepository
):ViewModel() {
    var updateUiTktsState by mutableStateOf(InsertUiTiketsState())
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

    private val id_tiket: Int = checkNotNull(savedStateHandle[DestinasiUpdateTkts.Id_Tiket])

    init {
        viewModelScope.launch {
            updateUiTktsState = tiketsRepository.getTiketsById(id_tiket).data
                .toUiStateTkts()
        }
    }

    fun updateTktsState(insertUiTiketsEvent: InsertUiTiketsEvent){
        updateUiTktsState = InsertUiTiketsState(insertUiTiketsEvent = insertUiTiketsEvent)
    }

    suspend fun updateTkts(){
        viewModelScope.launch {
            try {
                tiketsRepository.updateTikets(id_tiket, updateUiTktsState.insertUiTiketsEvent.toTkts())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}