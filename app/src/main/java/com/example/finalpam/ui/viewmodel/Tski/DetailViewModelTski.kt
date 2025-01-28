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
import com.example.finalpam.entitas.TiketsResponseDetail
import com.example.finalpam.entitas.TransaksiResponseDetail
import com.example.finalpam.repository.EventRepository
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.repository.TiketsRepository
import com.example.finalpam.repository.TransaksiRepository
import com.example.finalpam.ui.navigation.DestinasiDetailTkts
import com.example.finalpam.ui.navigation.DestinasiDetailTski
import com.example.finalpam.ui.viewmodel.Tkts.DetailUiTktsState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailUiTskiState {
    data class Success(val transaksi: TransaksiResponseDetail) : DetailUiTskiState()
    object Error : DetailUiTskiState()
    object Loading : DetailUiTskiState()
}

class DetailViewModelTski(
    savedStateHandle: SavedStateHandle,
    private val tski: TransaksiRepository,
    private val tiket: TiketsRepository,
    private val peserta: PesertaRepository,
    private val event: EventRepository
): ViewModel(){
    var detailUiTskiState: DetailUiTskiState by mutableStateOf(DetailUiTskiState.Loading)
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

    private val id_transaksi: Int = checkNotNull(savedStateHandle[DestinasiDetailTski.Id_Transaksi])

    init {
        getTransaksiById()
    }

    fun getTransaksiById() {
        viewModelScope.launch {
            detailUiTskiState = DetailUiTskiState.Loading
            detailUiTskiState = try {
                val transaksi = tski.getTransaksiById(id_transaksi)
                DetailUiTskiState.Success(transaksi)
            } catch (e: IOException) {
                DetailUiTskiState.Error
            } catch (e: HttpException) {
                DetailUiTskiState.Error
            }
        }
    }
}