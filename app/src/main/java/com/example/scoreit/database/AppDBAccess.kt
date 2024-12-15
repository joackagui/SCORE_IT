package com.example.scoreit.database

import android.app.Application
import androidx.room.Room

class AppDBAccess: Application(){
    val room = Room.databaseBuilder(
        applicationContext,
        AppDataBase::class.java,
        "SCORE IT-DATA BASE").build()
}
