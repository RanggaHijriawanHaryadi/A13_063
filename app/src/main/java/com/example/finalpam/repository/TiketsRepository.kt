package com.example.finalpam.repository

import com.example.finalpam.entitas.Tikets
import com.example.finalpam.entitas.TiketsResponse
import com.example.finalpam.entitas.TiketsResponseDetail
import com.example.finalpam.service.TiketsService
import java.io.IOException



class NetworkTiketsRepository(
   private val tiketsApiService: TiketsService
): TiketsRepository{
    override suspend fun getTikets(): TiketsResponse {
        return tiketsApiService.getTikets()
    }

    override suspend fun getTiketsById(id_tiket: Int): TiketsResponseDetail {
        return tiketsApiService.getTiketsById(id_tiket)
    }

    override suspend fun insertTikets(tikets: Tikets) {
        tiketsApiService.insertTikets(tikets)
    }

    override suspend fun updateTikets(id_tiket: Int, tikets: Tikets) {
        tiketsApiService.updateTikets(id_tiket, tikets)
    }

    override suspend fun deleteTikets(id_tiket: Int) {
        try {
            val response = tiketsApiService.deleteTikets(id_tiket)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Tiket. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

}