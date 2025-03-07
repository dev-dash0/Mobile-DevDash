package com.elfeky.devdash.di

import com.elfeky.domain.repo.ProjectRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.usecase.project.AddProjectUseCase
import com.elfeky.domain.usecase.project.DeleteProjectUseCase
import com.elfeky.domain.usecase.project.GetAllProjectsUseCase
import com.elfeky.domain.usecase.project.GetProjectByIdUseCase
import com.elfeky.domain.usecase.project.UpdateProjectUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProjectUceCaseModule {
    @Provides
    @Singleton
    fun provideAddProjectUseCase(
        repo: ProjectRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): AddProjectUseCase {
        return AddProjectUseCase(repo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideGetAllProjectsUseCase(
        repo: ProjectRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): GetAllProjectsUseCase {
        return GetAllProjectsUseCase(repo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideGetProjectByIdUseCase(
        repo: ProjectRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): GetProjectByIdUseCase {
        return GetProjectByIdUseCase(repo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideUpdateProjectUseCase(
        repo: ProjectRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): UpdateProjectUseCase {
        return UpdateProjectUseCase(repo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideDeleteProjectUseCase(
        repo: ProjectRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): DeleteProjectUseCase {
        return DeleteProjectUseCase(repo, accessTokenUseCase)
    }

}