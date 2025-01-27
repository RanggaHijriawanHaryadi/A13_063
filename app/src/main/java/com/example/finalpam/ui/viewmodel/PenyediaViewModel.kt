package com.example.finalpam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalpam.FestivalApplications
import com.example.finalpam.ui.viewmodel.Evnt.DetailViewModelEvnt
import com.example.finalpam.ui.viewmodel.Evnt.HomeViewModelEvnt
import com.example.finalpam.ui.viewmodel.Evnt.InsertViewModelEvnt
import com.example.finalpam.ui.viewmodel.Evnt.UpdateViewModelEvnt
import com.example.finalpam.ui.viewmodel.Pstr.DetailViewModelPstr
import com.example.finalpam.ui.viewmodel.Pstr.HomeViewModelPstr
import com.example.finalpam.ui.viewmodel.Pstr.InsertViewModelPstr
import com.example.finalpam.ui.viewmodel.Pstr.UpdateViewModelPstr
import com.example.finalpam.ui.viewmodel.Tkts.DetailViewModelTkts
import com.example.finalpam.ui.viewmodel.Tkts.HomeViewModelTkts
import com.example.finalpam.ui.viewmodel.Tkts.InsertViewModelTkts
import com.example.finalpam.ui.viewmodel.Tkts.UpdateViewModelTkts
import com.example.finalpam.ui.viewmodel.Tski.DetailViewModelTski
import com.example.finalpam.ui.viewmodel.Tski.HomeViewModelTski
import com.example.finalpam.ui.viewmodel.Tski.InsertViewModelTski
import com.example.finalpam.ui.viewmodel.Tski.UpdateViewModelTski


object PenyediaViewModel {
    val Factory = viewModelFactory {

        // Untuk Peserta
        initializer {
            HomeViewModelPstr(aplikasiFsvl().container.pesertaRepository)
        }
        initializer {
            InsertViewModelPstr(aplikasiFsvl().container.pesertaRepository)
        }
        initializer {
            DetailViewModelPstr(createSavedStateHandle(),aplikasiFsvl().container.pesertaRepository)
        }
        initializer {
            UpdateViewModelPstr(createSavedStateHandle(),aplikasiFsvl().container.pesertaRepository)
        }

        // Untuk Event
        initializer {
            HomeViewModelEvnt(aplikasiFsvl().container.eventRepository)
        }
        initializer {
            InsertViewModelEvnt(aplikasiFsvl().container.eventRepository)
        }
        initializer {
            DetailViewModelEvnt(createSavedStateHandle(),aplikasiFsvl().container.eventRepository)
        }
        initializer {
            UpdateViewModelEvnt(createSavedStateHandle(),aplikasiFsvl().container.eventRepository)
        }
    }
}

fun CreationExtras.aplikasiFsvl(): FestivalApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FestivalApplications)