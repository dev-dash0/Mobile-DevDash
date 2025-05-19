package com.elfeky.devdash.di

import android.content.Context
import android.content.SharedPreferences
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.usecase.local_storage.IsFirstLoginUseCase
import com.elfeky.domain.usecase.local_storage.LoginDataUseCase
import com.elfeky.domain.usecase.local_storage.RefreshTokenUseCase
import com.elfeky.domain.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideLoginDataUseCase(sharedPreferences: SharedPreferences): LoginDataUseCase =
        LoginDataUseCase(sharedPreferences)

    @Provides
    @Singleton
    fun provideIsFirstLoginUseCase(sharedPreferences: SharedPreferences): IsFirstLoginUseCase =
        IsFirstLoginUseCase(sharedPreferences)

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
        return context.getSharedPreferences(Constants.USER_DATA_FILE, Context.MODE_PRIVATE)
    }
}