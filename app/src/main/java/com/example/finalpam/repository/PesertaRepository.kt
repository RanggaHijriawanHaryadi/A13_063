package com.example.finalpam.repository

import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.PesertaResponse
import com.example.finalpam.entitas.PesertaResponseDetail

interface PesertaRepository {
    suspend fun getPeserta(): PesertaResponse
    suspend fun insertPeserta(peserta: Peserta)
    suspend fun updatePeserta(id_peserta:Int, peserta: Peserta)
    suspend fun deletePeserta(id_peserta: Int)
    suspend fun getPesertaById(id_peserta: Int):PesertaResponseDetail
}

