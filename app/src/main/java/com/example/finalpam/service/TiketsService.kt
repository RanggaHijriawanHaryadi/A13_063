package com.example.finalpam.service

import com.example.finalpam.entitas.Tikets
import com.example.finalpam.entitas.TiketsResponse
import com.example.finalpam.entitas.TiketsResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TiketsService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @GET(".")
    suspend fun getTikets() : TiketsResponse

    @GET("{id_tiket}")
    suspend fun  getTiketsById(@Path("id_tiket") id_tiket:Int): TiketsResponseDetail

    @POST("store2")
    suspend fun insertTikets(@Body tikets: Tikets)

    @PUT("{id_tiket}")
    suspend fun updateTikets(@Path("id_tiket") id_tiket: Int,@Body tikets: Tikets)

    @DELETE("{id_tiket}")
    suspend fun deleteTikets(@Path("id_tiket") id_tiket: Int) : Response<Void>
}