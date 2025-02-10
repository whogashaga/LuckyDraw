package com.example.luckydraw.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.luckydraw.model.Item
import com.example.luckydraw.model.ItemDao

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getItemDao(): ItemDao

}