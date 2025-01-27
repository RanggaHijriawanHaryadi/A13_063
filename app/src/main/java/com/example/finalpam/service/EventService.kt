package com.example.finalpam.service

import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.EventResponse
import com.example.finalpam.entitas.EventResponseDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET(".")
    suspend fun getEvent(): EventResponse

    @GET("{id_event}")
    suspend fun  getEventById(@Path("id_event") id_event:Int): EventResponseDetail

    @POST("store1")
    suspend fun insertEvent(@Body event: Event)

    @PUT("{id_event}")
    suspend fun updateEvent(@Path("id_event") id_event: Int, @Body event: Event)

    @DELETE("{id_event}")
    suspend fun deleteEvent(@Path("id_event") id_event: Int) : Response<Void>
}