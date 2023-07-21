package com.alphazetakapp.petsageoficial

import android.app.Application
import com.google.android.gms.ads.MobileAds

class PetAgeAddApp:Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this){}
    }
}