package com.elfeky.devdash.di

import com.elfeky.data.remote.AuthenticationApiService
import com.elfeky.data.remote.CompanyApiService
import com.elfeky.data.repo.AuthenticationRepoImpl
import com.elfeky.data.repo.CompanyRepoImpl
import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.repo.CompanyRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideAuthenticationRepo(authenticationApiService: AuthenticationApiService): AuthenticationRepo {
        return AuthenticationRepoImpl(authenticationApiService)
    }

    @Provides
    @Singleton
    fun provideCompanyRepo(companyApiService: CompanyApiService): CompanyRepo {
        return CompanyRepoImpl(companyApiService)
    }
}