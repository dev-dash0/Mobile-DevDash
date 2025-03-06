package com.elfeky.devdash.di

import com.elfeky.domain.repo.CompanyRepo
import com.elfeky.domain.usecase.company.AddCompanyUseCase
import com.elfeky.domain.usecase.company.GetCompaniesUseCase
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CompanyUseCaseModule {
    @Provides
    @Singleton
    fun provideAddCompanyUseCase(
        repo: CompanyRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): AddCompanyUseCase {
        return AddCompanyUseCase(repo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideGetCompaniesUseCase(
        repo: CompanyRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): GetCompaniesUseCase {
        return GetCompaniesUseCase(repo, accessTokenUseCase)
    }

}