package com.elfeky.devdash.di.usecase_module

import com.elfeky.domain.repo.BacklogRepo
import com.elfeky.domain.usecase.backlog.CreateIssueUseCase
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BacklogUseCaseModule {
    @Provides
    @Singleton
    fun provideCreateIssueUseCase(
        accessTokenUseCase: AccessTokenUseCase,
        backlogRepo: BacklogRepo,
    ): CreateIssueUseCase {
        return CreateIssueUseCase(repo = backlogRepo, accessTokenUseCase = accessTokenUseCase)
    }
}