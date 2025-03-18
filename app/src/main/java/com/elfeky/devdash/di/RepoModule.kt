package com.elfeky.devdash.di

import com.elfeky.data.remote.AuthenticationApiService
import com.elfeky.data.remote.DashBoardApiService
import com.elfeky.data.remote.PinApiService
import com.elfeky.data.remote.ProjectApiService
import com.elfeky.data.remote.TenantApiService
import com.elfeky.data.repo.AuthenticationRepoImpl
import com.elfeky.data.repo.DashBoardRepoImpl
import com.elfeky.data.repo.PinRepoImpl
import com.elfeky.data.repo.ProjectRepoImpl
import com.elfeky.data.repo.TenantRepoImpl
import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.repo.DashBoardRepo
import com.elfeky.domain.repo.PinRepo
import com.elfeky.domain.repo.ProjectRepo
import com.elfeky.domain.repo.TenantRepo
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
    fun provideTenantRepo(tenantApiService: TenantApiService): TenantRepo {
        return TenantRepoImpl(tenantApiService)
    }

    @Provides
    @Singleton
    fun provideProductRepo(projectApiService: ProjectApiService): ProjectRepo {
        return ProjectRepoImpl(projectApiService)
    }

    @Provides
    @Singleton
    fun providePinRepo(pinApiService: PinApiService): PinRepo {
        return PinRepoImpl(pinApiService)
    }

    @Provides
    @Singleton
    fun provideDashBoardRepo(dashBoardApiService: DashBoardApiService): DashBoardRepo{
        return DashBoardRepoImpl(dashBoardApiService)
    }
}