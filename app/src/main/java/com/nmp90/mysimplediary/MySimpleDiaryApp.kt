package com.nmp90.mysimplediary

import android.app.Application

class MySimpleDiaryApp : Application() {

    companion object {
        lateinit var instance: MySimpleDiaryApp
    }

    init{
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }

}