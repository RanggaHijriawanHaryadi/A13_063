package com.example.finalpam.dependenciesinjection

import com.example.finalpam.repository.EventRepository
import com.example.finalpam.repository.NetworkEventRepository
import com.example.finalpam.repository.NetworkPesertaRepository
import com.example.finalpam.repository.NetworkTiketsRepository
import com.example.finalpam.repository.NetworkTransaksiRepository
import com.example.finalpam.repository.PesertaRepository
import com.example.finalpam.repository.TiketsRepository
import com.example.finalpam.repository.TransaksiRepository
import com.example.finalpam.service.EventService
import com.example.finalpam.service.PesertaService
import com.example.finalpam.service.TiketsService
import com.example.finalpam.service.TransaksiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


interface AppContainer {
    val pesertaRepository: PesertaRepository
    val eventRepository: EventRepository
    val tiketsRepository: TiketsRepository
    val transaksiRepository: TransaksiRepository
}
class FestivalContainer : AppContainer {
    private val baseUrl= "http://10.0.2.2:3000/api/peserta/"
    private val baseUrlEvent = "http://10.0.2.2:3000/api/event/"
    private val baseUrlTikets = "http://10.0.2.2:3000/api/tikets/"
    private val baseUrlTransaksi = "http://10.0.2.2:3000/api/transaksi/"


    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()
    private val retrofitEvent: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlEvent).build()
    private val retrofitTikets: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlTikets).build()
    private val retrofitTransaksi: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrlTransaksi).build()
    // Peserta
    private val pesertaService: PesertaService by lazy {
        retrofit.create(PesertaService::class.java)
    }
    override val pesertaRepository: PesertaRepository by lazy {
        NetworkPesertaRepository(pesertaService)
    }

    // EVENT
    private val eventService: EventService by lazy {
        retrofitEvent.create(EventService::class.java)
    }
    override val eventRepository: EventRepository by lazy {
        NetworkEventRepository(eventService)
    }

    // Tikets
    private val tiketsService: TiketsService by lazy {
        retrofitTikets.create(TiketsService::class.java)
    }
    override val tiketsRepository: TiketsRepository by lazy {
        NetworkTiketsRepository(tiketsService)
    }

    //Transaksi
    private val transaksiService: TransaksiService by lazy {
        retrofitTransaksi.create(TransaksiService::class.java)
    }
    override val transaksiRepository: TransaksiRepository by lazy {
        NetworkTransaksiRepository(transaksiService)
    }

}