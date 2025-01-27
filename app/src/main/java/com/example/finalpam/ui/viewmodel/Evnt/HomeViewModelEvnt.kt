package com.example.finalpam.ui.viewmodel.Evnt


import androidx.lifecycle.ViewModel

import com.example.finalpam.entitas.Event
import com.example.finalpam.repository.EventRepository


sealed class HomeUiEventState {
    data class Success(val event: List<Event>) : HomeUiEventState()
    object Error : HomeUiEventState()
    object Loading : HomeUiEventState()
}

class HomeViewModelEvnt(
    private val evt: EventRepository
): ViewModel()