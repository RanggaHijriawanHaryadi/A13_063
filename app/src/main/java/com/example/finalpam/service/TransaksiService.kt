package com.example.finalpam.service

import com.example.finalpam.entitas.Tikets
import com.example.finalpam.entitas.TiketsResponse
import com.example.finalpam.entitas.TiketsResponseDetail
import com.example.finalpam.entitas.Transaksi
import com.example.finalpam.entitas.TransaksiResponse
import com.example.finalpam.entitas.TransaksiResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TransaksiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getTransaksi() : TransaksiResponse

    @GET("{id_transaksi}")
    suspend fun  getTransaksiById(@Path("id_transaksi") id_transaksi:Int): TransaksiResponseDetail

    @POST("store3")
    suspend fun insertTransaksi(@Body transaksi: Transaksi)

    @PUT("{id_transaksi}")
    suspend fun updateTransaksi(@Path("id_transaksi") id_transaksi: Int, @Body transaksi: Transaksi)

    @DELETE("{id_transaksi}")
    suspend fun deleteTransaksi(@Path("id_transaksi") id_transaksi: Int) : Response<Void>
}