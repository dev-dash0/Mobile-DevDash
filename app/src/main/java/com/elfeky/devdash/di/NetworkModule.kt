package com.elfeky.devdash.di

import com.elfeky.data.remote.AuthenticationApiService
import com.elfeky.data.remote.BacklogApiService
import com.elfeky.data.remote.DashBoardApiService
import com.elfeky.data.remote.IssueApiService
import com.elfeky.data.remote.PinApiService
import com.elfeky.data.remote.ProjectApiService
import com.elfeky.data.remote.SprintApiService
import com.elfeky.data.remote.SprintIssueApiService
import com.elfeky.data.remote.TenantApiService
import com.elfeky.domain.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient().newBuilder().addInterceptor(logging)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthenticationApiService(retrofit: Retrofit): AuthenticationApiService {
        return retrofit.create(AuthenticationApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCompanyApiService(retrofit: Retrofit): TenantApiService {
        return retrofit.create(TenantApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideProjectApiService(retrofit: Retrofit): ProjectApiService {
        return retrofit.create(ProjectApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePinApiService(retrofit: Retrofit): PinApiService {
        return retrofit.create(PinApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDashBoardApiService(retrofit: Retrofit): DashBoardApiService {
        return retrofit.create(DashBoardApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBacklogApiService(retrofit: Retrofit): BacklogApiService {
        return retrofit.create(BacklogApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSprintApiService(retrofit: Retrofit): SprintApiService {
        return retrofit.create(SprintApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideIssueApiService(retrofit: Retrofit): IssueApiService {
        return retrofit.create(IssueApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSprintIssueApiService(retrofit: Retrofit): SprintIssueApiService {
        return retrofit.create(SprintIssueApiService::class.java)
    }
}