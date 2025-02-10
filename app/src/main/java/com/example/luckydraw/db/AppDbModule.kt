package com.example.luckydraw.db

import android.content.Context
import androidx.room.Room
import com.example.luckydraw.model.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDbModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "app_database"
        ).build()


    @Singleton
    @Provides
    fun provideItemDao(db: AppDatabase): ItemDao = db.getItemDao()

}