package com.example.justcleantask.di

import com.example.data.api.PostsApi
import com.example.justcleantask.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.ClientInfoStatus
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun getApi(retrofit: Retrofit) : PostsApi=
        retrofit.create(PostsApi::class.java)

    @Provides
    @Singleton
    fun getRetrofit(client: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun getClient(interceptor:HttpLoggingInterceptor) =
            OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
    @Provides
    @Singleton
    fun getInterceptor() : HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level=HttpLoggingInterceptor.Level.BODY }
}