package com.example.finalpam.ui.viewmodel.Pstr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalpam.entitas.PesertaResponseDetail
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.ui.navigation.DestinasiDetailPstr

import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class DetailViewModelPstr(

) : ViewModel() {

    init {
        getPesertaById()
    }

    fun getPesertaById() {
        viewModelScope.launch {
            detailUiPstrState = DetailUiPstrState.Loading
            detailUiPstrState = try {
                val peserta = pstr.getPesertaById(id_peserta)
                DetailUiPstrState.Success(peserta)
            } catch (e: IOException) {
                DetailUiPstrState.Error
            } catch (e: HttpException) {
                DetailUiPstrState.Error
            }
        }
    }
}