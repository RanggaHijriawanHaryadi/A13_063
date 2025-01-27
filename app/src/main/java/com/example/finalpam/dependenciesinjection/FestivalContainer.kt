package com.example.finalpam.dependenciesinjection


import com.example.finalpam.repository.NetworkPesertaRepository
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.service.PesertaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainer {
    val pesertaRepository: PesertaRepository
}
class FestivalContainer : AppContainer {
    private val baseUrl= "http://10.0.2.2:3000/api/peserta/"


    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    // Peserta
    private val pesertaService: PesertaService by lazy {
        retrofit.create(PesertaService::class.java)
    }
    override val pesertaRepository: PesertaRepository by lazy {
        NetworkPesertaRepository(pesertaService)
    }

}