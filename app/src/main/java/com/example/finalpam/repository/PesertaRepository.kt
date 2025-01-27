package com.example.finalpam.repository

import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.PesertaResponse
import com.example.finalpam.entitas.PesertaResponseDetail
import com.example.finalpam.service.PesertaService
import java.io.IOException

interface PesertaRepository {
    suspend fun getPeserta(): PesertaResponse
    suspend fun insertPeserta(peserta: Peserta)
    suspend fun updatePeserta(id_peserta:Int, peserta: Peserta)
    suspend fun deletePeserta(id_peserta: Int)
    suspend fun getPesertaById(id_peserta: Int):PesertaResponseDetail
}

class NetworkPesertaRepository(
    private val pesertaApiService: PesertaService
) : PesertaRepository{

    override suspend fun getPeserta(): PesertaResponse {
        return pesertaApiService.getPeserta()
    }

    override suspend fun insertPeserta(peserta: Peserta) {
        pesertaApiService.insertPeserta(peserta)
    }

    override suspend fun updatePeserta(id_peserta: Int, peserta: Peserta) {
        pesertaApiService.updatePeserta(id_peserta, peserta)
    }

    override suspend fun deletePeserta(id_peserta: Int) {
        try {
            val response = pesertaApiService.deletePeserta(id_peserta)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete peserta. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

    override suspend fun getPesertaById(id_peserta: Int): PesertaResponseDetail {
        return  pesertaApiService.getPesertaById(id_peserta)
    }
}