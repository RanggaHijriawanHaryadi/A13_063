package com.example.finalpam.ui.viewmodel.Pstr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.ui.navigation.DestinasiUpdatePstr
import kotlinx.coroutines.launch

class UpdateViewModelPstr (
    savedStateHandle: SavedStateHandle,
    private val pesertaRepository: PesertaRepository
): ViewModel() {
    var updateUiPstrState by mutableStateOf(InsertUiPesertaState())
        private set

    private val id_peserta: Int = checkNotNull(savedStateHandle[DestinasiUpdatePstr.Id_Peserta])

    init {
        viewModelScope.launch {
            updateUiPstrState = pesertaRepository.getPesertaById(id_peserta).data
                .toUiStatePstr()
        }
    }

    fun updatePstrState(insertUiPesertaEvent: InsertUiPesertaEvent){
        updateUiPstrState = InsertUiPesertaState(insertUiPesertaEvent = insertUiPesertaEvent)
    }

    suspend fun updatePstr(){
        viewModelScope.launch {
            try {
                pesertaRepository.updatePeserta(id_peserta, updateUiPstrState.insertUiPesertaEvent.toPstr())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}