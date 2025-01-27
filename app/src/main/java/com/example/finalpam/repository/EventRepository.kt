package com.example.finalpam.repository

import com.example.finalpam.entitas.Event
import com.example.finalpam.entitas.EventResponse
import com.example.finalpam.entitas.EventResponseDetail
import com.example.finalpam.service.EventService
import java.io.IOException




class NetworkEventRepository(
    private val eventApiService: EventService
): EventRepository{
    override suspend fun getEvent(): EventResponse {
        return eventApiService.getEvent()
    }

    override suspend fun insertEvent(event: Event) {
        eventApiService.insertEvent(event)
    }

    override suspend fun updateEvent(id_event: Int, event: Event) {
        eventApiService.updateEvent(id_event,event)
    }

    override suspend fun deleteEvent(id_event: Int) {
        try {
            val response = eventApiService.deleteEvent(id_event)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete event. HTTP Status Code: " +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }

    override suspend fun getEventById(id_event: Int): EventResponseDetail {
        return  eventApiService.getEventById(id_event)
    }
}