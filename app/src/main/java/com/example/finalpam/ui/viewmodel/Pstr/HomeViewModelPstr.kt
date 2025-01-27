package com.example.finalpam.ui.viewmodel.Pstr


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