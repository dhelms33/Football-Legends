package edu.msudenver.cs3013.project03
import android.app.Application

class PlayerNameApplication : Application() {


        lateinit var playerStore: PlayerStore
        override fun onCreate() {
            super.onCreate()
            playerStore = PlayerStore(this)
        }
    }
