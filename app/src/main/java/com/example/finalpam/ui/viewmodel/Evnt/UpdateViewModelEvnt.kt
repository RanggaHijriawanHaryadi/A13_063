package com.example.finalpam.ui.viewmodel.Evnt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalpam.repository.EventRepository
import com.example.finalpam.ui.navigation.DestinasiUpdateEvnt
import kotlinx.coroutines.launch

class UpdateViewModelEvnt(
    savedStateHandle: SavedStateHandle,
    private val evnt: EventRepository
): ViewModel() {
        var updateUiEvntState by mutableStateOf(InsertUiEvntState())
        private set

    private val id_event: Int = checkNotNull(savedStateHandle[DestinasiUpdateEvnt.Id_Event])

    init {
        viewModelScope.launch {
            updateUiEvntState = evnt.getEventById(id_event).data
                .toUiStateEvnt()
        }
    }

    fun updateEventState(insertUiEvntEvent: InsertUiEvntEvent){
        updateUiEvntState = InsertUiEvntState(insertUiEvntEvent =  insertUiEvntEvent)
    }

    suspend fun updateEvnt(){
        viewModelScope.launch {
            try {
                evnt.updateEvent(id_event, updateUiEvntState.insertUiEvntEvent.toEvnt())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}