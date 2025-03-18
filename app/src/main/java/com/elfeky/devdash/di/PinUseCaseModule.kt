package com.elfeky.devdash.di

import com.elfeky.domain.repo.PinRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.usecase.pin.GetPinnedItemsUseCase
import com.elfeky.domain.usecase.pin.PinItemUseCase
import com.elfeky.domain.usecase.pin.UnpinItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PinUseCaseModule {
    @Provides
    @Singleton
    fun providePinItemUseCase(
        repo: PinRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): PinItemUseCase =
        PinItemUseCase(repo, accessTokenUseCase)

    @Provides
    @Singleton
    fun provideUnpinItemUseCase(
        repo: PinRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): UnpinItemUseCase = UnpinItemUseCase(repo, accessTokenUseCase)

    @Provides
    @Singleton
    fun provideGetPinnedItemsUseCase(
        repo: PinRepo,
        accessTokenUseCase: AccessTokenUseCase
    ): GetPinnedItemsUseCase = GetPinnedItemsUseCase(repo, accessTokenUseCase)
}