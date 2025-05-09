package com.elfeky.devdash.di.usecase_module

import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.usecase.account.ChangePasswordUseCase
import com.elfeky.domain.usecase.account.DeleteAccountUseCase
import com.elfeky.domain.usecase.account.GetUserProfileUseCase
import com.elfeky.domain.usecase.account.LoginUserUseCase
import com.elfeky.domain.usecase.account.LogoutUseCase
import com.elfeky.domain.usecase.account.RegisterUserUseCase
import com.elfeky.domain.usecase.account.UpdateProfileUseCase
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.usecase.local_storage.IsFirstLoginUseCase
import com.elfeky.domain.usecase.local_storage.LoginDataUseCase
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
        refreshTokenUseCase: RefreshTokenUseCase,
        loginDataUseCase: LoginDataUseCase,
        isFirstLoginUseCase: IsFirstLoginUseCase
    ): LoginUserUseCase {
        return LoginUserUseCase(
            authenticationRepo,
            accessTokenUseCase,
            refreshTokenUseCase,
            loginDataUseCase,
            isFirstLoginUseCase
        )
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