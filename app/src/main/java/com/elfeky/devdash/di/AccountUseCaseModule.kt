package com.elfeky.devdash.di

import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.usecase.ChangePasswordUseCase
import com.elfeky.domain.usecase.DeleteAccountUseCase
import com.elfeky.domain.usecase.GetUserProfileUseCase
import com.elfeky.domain.usecase.LoginUserUseCase
import com.elfeky.domain.usecase.LogoutUseCase
import com.elfeky.domain.usecase.RegisterUserUseCase
import com.elfeky.domain.usecase.UpdateProfileUseCase
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.usecase.local_storage.RefreshTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountUseCaseModule {
    @Provides
    @Singleton
    fun provideLoginUseCase(
        authenticationRepo: AuthenticationRepo,
        accessTokenUseCase: AccessTokenUseCase,
        refreshTokenUseCase: RefreshTokenUseCase
    ): LoginUserUseCase {
        return LoginUserUseCase(authenticationRepo, accessTokenUseCase, refreshTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(authenticationRepo: AuthenticationRepo): RegisterUserUseCase {
        return RegisterUserUseCase(authenticationRepo)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        authenticationRepo: AuthenticationRepo,
        accessTokenUseCase: AccessTokenUseCase,
        refreshTokenUseCase: RefreshTokenUseCase
    ): LogoutUseCase {
        return LogoutUseCase(authenticationRepo, accessTokenUseCase, refreshTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideGetUserProfileUseCase(
        authenticationRepo: AuthenticationRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): GetUserProfileUseCase {
        return GetUserProfileUseCase(authenticationRepo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideDeleteAccountUseCase(
        authenticationRepo: AuthenticationRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): DeleteAccountUseCase {
        return DeleteAccountUseCase(authenticationRepo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideChangePasswordUseCase(
        authenticationRepo: AuthenticationRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): ChangePasswordUseCase {
        return ChangePasswordUseCase(authenticationRepo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(
        authenticationRepo: AuthenticationRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): UpdateProfileUseCase {
        return UpdateProfileUseCase(authenticationRepo, accessTokenUseCase)
    }
}