package com.example.finalpam.ui.viewmodel.Pstr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.repository.PesertaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiPesertaState {
    data class Success(val peserta: List<Peserta>) : HomeUiPesertaState()
    object Error : HomeUiPesertaState()
    object Loading : HomeUiPesertaState()
}

class HomeViewModelPstr(
    private val pst: PesertaRepository
) : ViewModel(){
    var pstrUIState: HomeUiPesertaState by mutableStateOf(HomeUiPesertaState.Loading)
        private set

    init {
        getPstr()
    }

    fun getPstr(){
        viewModelScope.launch {
            pstrUIState = HomeUiPesertaState.Loading
            pstrUIState = try {
                HomeUiPesertaState.Success(pst.getPeserta().data)
            } catch (e: IOException) {
                HomeUiPesertaState.Error
            } catch (e: HttpException){
                HomeUiPesertaState.Error
            }
        }
    }

    fun deletePstr(id_peserta: Int) {
        viewModelScope.launch {
            try {
                pst.deletePeserta(id_peserta)
            } catch (e: IOException) {
                HomeUiPesertaState.Error
            } catch (e: HttpException) {
                HomeUiPesertaState.Error
            }
        }
    }
}