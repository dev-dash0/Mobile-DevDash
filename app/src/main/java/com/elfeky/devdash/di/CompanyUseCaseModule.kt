package com.elfeky.devdash.di

import com.elfeky.domain.repo.CompanyRepo
import com.elfeky.domain.usecase.AddCompanyUseCase
import com.elfeky.domain.usecase.GetCompaniesUseCase
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
    fun provideAddCompanyUseCase(repo: CompanyRepo): AddCompanyUseCase {
        return AddCompanyUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetCompaniesUseCase(repo: CompanyRepo): GetCompaniesUseCase {
        return GetCompaniesUseCase(repo)
    }

}