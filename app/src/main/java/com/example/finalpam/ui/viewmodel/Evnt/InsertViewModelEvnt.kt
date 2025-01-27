package com.example.finalpam.ui.viewmodel.Evnt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalpam.entitas.Event
import com.example.finalpam.repository.EventRepository
import kotlinx.coroutines.launch

class InsertViewModelEvnt(private val evnt: EventRepository
):ViewModel (){
    var uiStateEvnt by mutableStateOf(InsertUiEvntState())
        private set

    fun updateInsertEvntState(insertUiEvntEvent: InsertUiEvntEvent) {
        uiStateEvnt = InsertUiEvntState(insertUiEvntEvent = insertUiEvntEvent)
    }

    suspend fun insertEvnt(){
        viewModelScope.launch {
            try {
                evnt.insertEvent(uiStateEvnt.insertUiEvntEvent.toEvnt())
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiEvntState(
    val insertUiEvntEvent: InsertUiEvntEvent = InsertUiEvntEvent(),
    )

fun Event.toUiStateEvnt(): InsertUiEvntState = InsertUiEvntState(
    insertUiEvntEvent = toInsertUiEvntEvent()
)

fun InsertUiEvntEvent.toEvnt(): Event = Event (
    id_event = id_event ?: 0,
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    tanggal_event = tanggal_event,
    lokasi_event = lokasi_event
)


fun Event.toInsertUiEvntEvent(): InsertUiEvntEvent = InsertUiEvntEvent (
    id_event = id_event,
    nama_event = nama_event,
    deskripsi_event = deskripsi_event,
    tanggal_event = tanggal_event,
    lokasi_event = lokasi_event
)

data class InsertUiEvntEvent(
    val id_event: Int ?= 0,
    val nama_event: String = "",
    val deskripsi_event: String = "",
    val tanggal_event: String = "",
    val lokasi_event: String = ""
)