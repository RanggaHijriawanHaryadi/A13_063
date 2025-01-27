package com.example.finalpam.ui.viewmodel.Evnt


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException




class DetailViewModelEvnt(

): ViewModel() {


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