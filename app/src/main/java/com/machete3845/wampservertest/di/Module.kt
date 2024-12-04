package com.machete3845.wampservertest.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.machete3845.wampservertest.api.AuthApi
import com.machete3845.wampservertest.apis.IncidentApi
import com.machete3845.wampservertest.apis.MainApi
import com.machete3845.wampservertest.apis.MessageApi
import com.machete3845.wampservertest.apis.ServiceApi
import com.machete3845.wampservertest.utils.UserSession
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.10/host/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageApi(retrofit: Retrofit): MessageApi {
        return retrofit.create(MessageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideServiceApi(retrofit: Retrofit): ServiceApi {
        return retrofit.create(ServiceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideIncidentApi(retrofit: Retrofit): IncidentApi {
        return retrofit.create(IncidentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserSession(): UserSession {
        return UserSession
    }



}