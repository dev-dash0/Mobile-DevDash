package com.elfeky.devdash.di

import android.content.Context
import android.content.SharedPreferences
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.usecase.local_storage.RefreshTokenUseCase
import com.elfeky.domain.util.Constants.USER_DATA_FILE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesUseCase {

    @Provides
    @Singleton
    fun provideAccessTokenUseCase(sharedPreferences: SharedPreferences): AccessTokenUseCase =
        AccessTokenUseCase(sharedPreferences)

    @Provides
    @Singleton
    fun provideRefreshTokenUseCase(sharedPreferences: SharedPreferences): RefreshTokenUseCase =
        RefreshTokenUseCase(sharedPreferences)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(USER_DATA_FILE, Context.MODE_PRIVATE)
    }
}