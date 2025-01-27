package com.example.finalpam.ui.viewmodel.Evnt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalpam.entitas.EventResponseDetail
import com.example.finalpam.repository.EventRepository
import com.example.finalpam.ui.navigation.DestinasiDetailEvnt
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class DetailUiEvntState {
    data class Success(val event: EventResponseDetail) : DetailUiEvntState()
    object Error : DetailUiEvntState()
    object Loading : DetailUiEvntState()
}

class DetailViewModelEvnt(
    savedStateHandle: SavedStateHandle,
    private val evnt: EventRepository
): ViewModel() {
    var detailUiEvntState: DetailUiEvntState by mutableStateOf(
        DetailUiEvntState.Loading)
        private set

    private val id_event: Int = checkNotNull(savedStateHandle[DestinasiDetailEvnt.Id_Event])

    init {
        getEventById()
    }

    fun getEventById() {
        viewModelScope.launch {
            detailUiEvntState = DetailUiEvntState.Loading
            detailUiEvntState = try {
                val event = evnt.getEventById(id_event)
                DetailUiEvntState.Success(event)
            } catch (e: IOException) {
                DetailUiEvntState.Error
            } catch (e: HttpException) {
                DetailUiEvntState.Error
            }
        }
    }
}