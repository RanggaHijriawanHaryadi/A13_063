package com.example.finalpam.ui.viewmodel.Tski

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.Tikets

import com.example.finalpam.entitas.Transaksi
import com.example.finalpam.repository.EventRepository
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.repository.TiketsRepository

import com.example.finalpam.repository.TransaksiRepository

import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiTransaksiState {
    data class Success(val transaksi: List<Transaksi>) : HomeUiTransaksiState()
    object Error : HomeUiTransaksiState()
    object Loading : HomeUiTransaksiState()
}
//
class HomeViewModelTski(
    private val tsk: TransaksiRepository,
    private val tiket: TiketsRepository,
    private val peserta: PesertaRepository,
    private val event: EventRepository

): ViewModel(){
    var tskiUIState: HomeUiTransaksiState by mutableStateOf(HomeUiTransaksiState.Loading)
        private set

    // list tiket
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


    init {
        getTski()
    }

    fun getTski(){
        viewModelScope.launch {
            tskiUIState = HomeUiTransaksiState.Loading
            tskiUIState= try {
                HomeUiTransaksiState.Success(tsk.getTransaksi().data)
            } catch (e: IOException) {
                HomeUiTransaksiState.Error
            } catch (e: HttpException){
                HomeUiTransaksiState.Error
            }
        }
    }

    fun deleteTski(id_transaksi: Int) {
        viewModelScope.launch {
            try {
                tsk.deleteTransaksi(id_transaksi)
            } catch (e: IOException) {
                HomeUiTransaksiState.Error
            } catch (e: HttpException) {
                HomeUiTransaksiState.Error
            }
        }
    }
}