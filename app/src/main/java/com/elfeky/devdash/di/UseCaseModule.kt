package com.elfeky.devdash.di

import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.usecase.LoginUserUseCase
import com.elfeky.domain.usecase.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideLoginUseCase(authenticationRepo: AuthenticationRepo): LoginUserUseCase {
        return LoginUserUseCase(authenticationRepo)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(authenticationRepo: AuthenticationRepo): RegisterUserUseCase {
        return RegisterUserUseCase(authenticationRepo)
    }
}