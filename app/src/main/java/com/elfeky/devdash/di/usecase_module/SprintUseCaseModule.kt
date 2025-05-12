package com.elfeky.devdash.di.usecase_module

import com.elfeky.domain.repo.SprintRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.usecase.sprint.CreateSprintUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SprintUseCaseModule {
    @Provides
    @Singleton
    fun provideCreateSprintUseCase(
        accessTokenUseCase: AccessTokenUseCase,
        sprintRepo: SprintRepo
    ): CreateSprintUseCase {
        return CreateSprintUseCase(accessTokenUseCase = accessTokenUseCase, repo = sprintRepo)
    }
}