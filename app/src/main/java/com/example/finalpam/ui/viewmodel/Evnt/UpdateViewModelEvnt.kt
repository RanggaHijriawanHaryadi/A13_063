package com.example.finalpam.ui.viewmodel.Evnt


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch

class UpdateViewModelEvnt(

): ViewModel() {


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