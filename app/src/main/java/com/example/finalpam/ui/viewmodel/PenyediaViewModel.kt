package com.example.finalpam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalpam.FestivalApplications
import com.example.finalpam.ui.viewmodel.Pstr.DetailViewModelPstr
import com.example.finalpam.ui.viewmodel.Pstr.HomeViewModelPstr
import com.example.finalpam.ui.viewmodel.Pstr.InsertViewModelPstr
import com.example.finalpam.ui.viewmodel.Pstr.UpdateViewModelPstr


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
    }
}

fun CreationExtras.aplikasiFsvl(): FestivalApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FestivalApplications)