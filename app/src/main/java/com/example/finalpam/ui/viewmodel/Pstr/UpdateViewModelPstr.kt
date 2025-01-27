package com.example.finalpam.ui.viewmodel.Pstr


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UpdateViewModelPstr (
): ViewModel() {

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