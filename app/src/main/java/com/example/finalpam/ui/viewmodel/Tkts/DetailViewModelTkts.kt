package com.example.finalpam.ui.viewmodel.Tkts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.TiketsResponseDetail
import com.example.finalpam.repository.EventRepository
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.repository.TiketsRepository
import com.example.finalpam.ui.navigation.DestinasiDetailTkts
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class DetailUiTktsState {
    data class Success(val tikets: TiketsResponseDetail) : DetailUiTktsState()
    object Error : DetailUiTktsState()
    object Loading : DetailUiTktsState()
}

class DetailViewModelTkts(
    savedStateHandle: SavedStateHandle,
    private val tktr: TiketsRepository,
    private val peserta: PesertaRepository,
    private val event: EventRepository
):ViewModel() {
    var detailUiTktsState: DetailUiTktsState by mutableStateOf(DetailUiTktsState.Loading)
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

    private val id_tiket: Int = checkNotNull(savedStateHandle[DestinasiDetailTkts.Id_Tiket])

    init {
        getTiketById()
    }

    fun getTiketById() {
        viewModelScope.launch {
            detailUiTktsState = DetailUiTktsState.Loading
            detailUiTktsState = try {
                val tikets = tktr.getTiketsById(id_tiket)
                DetailUiTktsState.Success(tikets)
            } catch (e: IOException) {
                DetailUiTktsState.Error
            } catch (e: HttpException) {
                DetailUiTktsState.Error
            }
        }
    }
}