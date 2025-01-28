package com.example.finalpam.ui.viewmodel.Tkts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.Tikets
import com.example.finalpam.repository.EventRepository
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.repository.TiketsRepository
import kotlinx.coroutines.launch
import java.io.IOException




class HomeViewModelTkts (
    private val tkt: TiketsRepository,
    private val peserta: PesertaRepository,
    private val event: EventRepository
):ViewModel(){
    var tktsUIState: HomeUiTikesState by mutableStateOf(HomeUiTikesState.Loading)
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

    init {
        getTkts()
    }

    fun getTkts(){
        viewModelScope.launch {
            tktsUIState = HomeUiTikesState.Loading
            tktsUIState = try {
                HomeUiTikesState.Success(tkt.getTikets().data)
            } catch (e: IOException) {
                HomeUiTikesState.Error
            } catch (e: HttpException){
                HomeUiTikesState.Error
            }
        }
    }

    fun deleteTkts(id_tikets: Int) {
        viewModelScope.launch {
            try {
                tkt.deleteTikets(id_tikets)
            } catch (e: IOException) {
                HomeUiTikesState.Error
            } catch (e: HttpException) {
                HomeUiTikesState.Error
            }
        }
    }
}