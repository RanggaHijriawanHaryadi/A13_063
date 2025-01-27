package com.example.finalpam

import android.app.Application
import com.example.finalpam.dependenciesinjection.AppContainer
import com.example.finalpam.dependenciesinjection.FestivalContainer


class FestivalApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = FestivalContainer()
    }
}