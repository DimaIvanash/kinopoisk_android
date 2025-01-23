package com.example.cinema.di

import com.example.cinema.data.PageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://kinopoiskapiunofficial.tech"
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideRetrofit(): PageApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PageApi::class.java)
    }

}
