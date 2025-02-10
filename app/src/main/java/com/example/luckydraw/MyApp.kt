package com.example.luckydraw

import android.app.Application
import androidx.room.Room
import com.example.luckydraw.db.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlin.properties.Delegates

@HiltAndroidApp
class MyApp: Application() {

//    companion object {
//        var instance: MyApp by Delegates.notNull()
//        var database: AppDatabase by Delegates.notNull()
//    }
//
//    override fun onCreate() {
//        super.onCreate()
//        instance = this
//        database = Room.databaseBuilder(
//            context = applicationContext,
//            klass = AppDatabase::class.java,
//            name = "my_database"
//        ).build()
//    }

}