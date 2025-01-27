package com.example.finalpam.ui.viewmodel.Pstr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.repository.PesertaRepository
import kotlinx.coroutines.launch

class InsertViewModelPstr (
    private val pstr: PesertaRepository
): ViewModel() {

    var uiStatePstr by mutableStateOf(InsertUiPesertaState())
        private set
    fun updateInsertPstrState(insertUiPesertaEvent: InsertUiPesertaEvent) {
        uiStatePstr = InsertUiPesertaState(insertUiPesertaEvent = insertUiPesertaEvent)
    }
    suspend fun insertPstr(){
        viewModelScope.launch {
            try {
                pstr.insertPeserta(uiStatePstr.insertUiPesertaEvent.toPstr())
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiPesertaState(
    val insertUiPesertaEvent: InsertUiPesertaEvent = InsertUiPesertaEvent(),


)

fun Peserta.toUiStatePstr(): InsertUiPesertaState = InsertUiPesertaState(
    insertUiPesertaEvent = toInsertUiPstrEvent()
)

fun InsertUiPesertaEvent.toPstr(): Peserta = Peserta (
    id_peserta = id_peserta ?: 0,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon,
)


fun Peserta.toInsertUiPstrEvent(): InsertUiPesertaEvent = InsertUiPesertaEvent (
    id_peserta = id_peserta,
    nama_peserta = nama_peserta,
    email = email,
    nomor_telepon = nomor_telepon,
)

data class InsertUiPesertaEvent(
    val id_peserta: Int ?= 0,
    val nama_peserta: String = "",
    val email: String = "",
    val nomor_telepon: String = "",
)