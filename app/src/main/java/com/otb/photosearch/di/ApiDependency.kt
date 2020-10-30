package com.otb.photosearch.di

import com.otb.photosearch.network.service.FlickrApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideFlickrApi(retrofit: Retrofit) = retrofit.create(FlickrApiService::class.java)
    factory { provideFlickrApi(get()) }
}