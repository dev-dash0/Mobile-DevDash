package com.elfeky.devdash.di

import com.elfeky.data.remote.sse.SseClient
import com.elfeky.data.repo.AgentRepoImpl
import com.elfeky.domain.repo.AgentRepo
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AgentModule {
    @Provides
    @Singleton
    fun provideSseClient(okHttpClient: OkHttpClient): SseClient = SseClient()

    @Provides
    @Singleton
    fun provideAgentRepository(
        gson: Gson,
        sseClient: SseClient
    ): AgentRepo = AgentRepoImpl(gson, sseClient)
}