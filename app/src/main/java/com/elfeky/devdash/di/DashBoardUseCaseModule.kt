package com.elfeky.devdash.di

import com.elfeky.domain.repo.DashBoardRepo
import com.elfeky.domain.usecase.dashboard.GetCalendarUseCase
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DashBoardUseCaseModule {
    @Provides
    @Singleton
    fun provideGetUserProfileUseCase(
        dashBoardRepo: DashBoardRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): GetCalendarUseCase {
        return GetCalendarUseCase(dashBoardRepo, accessTokenUseCase)
    }
}