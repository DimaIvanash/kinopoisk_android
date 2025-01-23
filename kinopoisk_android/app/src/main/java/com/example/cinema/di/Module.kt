package com.example.cinema.di

import android.app.Application
import androidx.room.Dao
import androidx.room.Room
import com.example.cinema.data.dataBases.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideMainDB(app: Application): AppDataBase {
        return Room.databaseBuilder(
            app,
            AppDataBase::class.java,
            "db"
        ).build()
    }

    @Provides
    fun provideDao(appDataBase: AppDataBase): com.example.cinema.data.dataBases.Dao {
        return appDataBase.dao()
    }
}
