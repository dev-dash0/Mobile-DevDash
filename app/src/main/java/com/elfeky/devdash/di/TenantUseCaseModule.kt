package com.elfeky.devdash.di

import com.elfeky.domain.repo.TenantRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.usecase.tenant.AddTenantUseCase
import com.elfeky.domain.usecase.tenant.DeleteTenantUseCase
import com.elfeky.domain.usecase.tenant.GetTenantByIdUseCase
import com.elfeky.domain.usecase.tenant.GetAllTenantsUseCase
import com.elfeky.domain.usecase.tenant.UpdateTenantUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TenantUseCaseModule {
    @Provides
    @Singleton
    fun provideAddCompanyUseCase(
        repo: TenantRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): AddTenantUseCase {
        return AddTenantUseCase(repo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideGetCompaniesUseCase(
        repo: TenantRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): GetAllTenantsUseCase {
        return GetAllTenantsUseCase(repo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideGetCompanyByIdUseCase(
        repo: TenantRepo,
        accessTokenUseCase: AccessTokenUseCase
        ): GetTenantByIdUseCase {
        return GetTenantByIdUseCase(repo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideUpdateCompanyUseCase(
        repo: TenantRepo,
        accessTokenUseCase: AccessTokenUseCase
        ): UpdateTenantUseCase {
        return UpdateTenantUseCase(repo, accessTokenUseCase)
    }

    @Provides
    @Singleton
    fun provideDeleteCompanyUseCase(
        repo: TenantRepo,
        accessTokenUseCase: AccessTokenUseCase
        ): DeleteTenantUseCase {
        return DeleteTenantUseCase(repo, accessTokenUseCase)
    }

}