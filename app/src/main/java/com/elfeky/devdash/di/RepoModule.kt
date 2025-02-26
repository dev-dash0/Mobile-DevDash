package com.elfeky.devdash.di

import com.elfeky.data.remote.AuthenticationApiService
import com.elfeky.data.repo.AuthenticationRepoImpl
import com.elfeky.domain.repo.AuthenticationRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideAuthenticationRepo(authenticationApiService: AuthenticationApiService): AuthenticationRepo {
        return AuthenticationRepoImpl(authenticationApiService)
    }
}