package com.example.finalpam.service

import com.example.finalpam.entitas.Peserta
import com.example.finalpam.entitas.PesertaResponse
import com.example.finalpam.entitas.PesertaResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PesertaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getPeserta() : PesertaResponse

    @GET("{id_peserta}")
    suspend fun  getPesertaById(@Path("id_peserta") id_peserta:Int): PesertaResponseDetail

    @POST("store")
    suspend fun insertPeserta(@Body peserta: Peserta)

    @PUT("{id_peserta}")
    suspend fun updatePeserta(@Path("id_peserta") id_peserta: Int, @Body peserta: Peserta)

    @DELETE("{id_peserta}")
    suspend fun deletePeserta(@Path("id_peserta") id_peserta: Int) : Response<Void>
}