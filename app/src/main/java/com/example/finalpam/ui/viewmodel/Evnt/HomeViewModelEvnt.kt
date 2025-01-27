package com.example.finalpam.ui.viewmodel.Evnt

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.finalpam.entitas.Event
import com.example.finalpam.repository.EventRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiEventState {
    data class Success(val event: List<Event>) : HomeUiEventState()
    object Error : HomeUiEventState()
    object Loading : HomeUiEventState()
}

class HomeViewModelEvnt(
    private val evt: EventRepository
): ViewModel() {
    var evntUIState: HomeUiEventState by mutableStateOf(HomeUiEventState.Loading)
        private set

    init {
        getEvnt()
    }

    fun getEvnt(){
        viewModelScope.launch {
            evntUIState = HomeUiEventState.Loading
            evntUIState = try {
                HomeUiEventState.Success(evt.getEvent().data)
            } catch (e: IOException) {
                HomeUiEventState.Error
            } catch (e: HttpException){
                HomeUiEventState.Error
            }
        }
    }

    fun deleteEvnt(id_event: Int) {
        viewModelScope.launch {
            try {
                evt.deleteEvent(id_event)
            } catch (e: IOException) {
                HomeUiEventState.Error
            } catch (e: HttpException) {
                HomeUiEventState.Error
            }
        }
    }
}