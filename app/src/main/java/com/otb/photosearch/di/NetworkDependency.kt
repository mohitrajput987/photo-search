package com.otb.photosearch.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.otb.photosearch.BuildConfig
import com.otb.photosearch.network.ApiInterceptor
import com.otb.photosearch.network.NetworkReachabilityInterceptor
import com.youtility.energyswitchcui.utils.NetworkStateChecker
import com.youtility.energyswitchcui.utils.NetworkStateCheckerImpl
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    fun provideOkHttpClient(networkStateChecker: NetworkStateChecker): OkHttpClient {
        val timeoutInSeconds = 120
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        val builder = OkHttpClient.Builder()
            .connectTimeout(timeoutInSeconds.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeoutInSeconds.toLong(), TimeUnit.SECONDS)
            .dispatcher(dispatcher)
            .addInterceptor(NetworkReachabilityInterceptor(networkStateChecker))
            .addInterceptor(ApiInterceptor())

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    factory { provideGson() }
    factory { NetworkStateCheckerImpl(get()) as NetworkStateChecker }
    factory { provideOkHttpClient(get()) }
    factory { provideRetrofit(get(), get()) }
}

