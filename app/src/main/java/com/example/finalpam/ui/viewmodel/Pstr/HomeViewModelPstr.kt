package com.example.finalpam.ui.viewmodel.Pstr


import androidx.lifecycle.ViewModel
import com.example.finalpam.entitas.Peserta
import com.example.finalpam.repository.PesertaRepository

sealed class HomeUiPesertaState {
    data class Success(val peserta: List<Peserta>) : HomeUiPesertaState()
    object Error : HomeUiPesertaState()
    object Loading : HomeUiPesertaState()
}

class HomeViewModelPstr(
    private val pst: PesertaRepository
) : ViewModel(){

}