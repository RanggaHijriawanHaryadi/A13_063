package com.example.finalpam.ui.viewmodel.Tkts


import androidx.lifecycle.ViewModel

import com.example.finalpam.repository.EventRepository
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.repository.TiketsRepository





class HomeViewModelTkts (
    private val tkt: TiketsRepository,
    private val peserta: PesertaRepository,
    private val event: EventRepository
):ViewModel(){

}