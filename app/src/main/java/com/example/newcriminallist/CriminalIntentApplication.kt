package com.example.newcriminallist

import android.app.Application
import com.example.newcriminallist.data.CrimeRepository

class CriminalIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}