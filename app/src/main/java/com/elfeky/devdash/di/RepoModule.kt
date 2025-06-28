package com.elfeky.devdash.di

import com.elfeky.data.repo.AssignUserIssueRepoImpl
import com.elfeky.data.repo.AuthenticationRepoImpl
import com.elfeky.data.repo.BacklogRepoImpl
import com.elfeky.data.repo.DashBoardRepoImpl
import com.elfeky.data.repo.IssueRepoImpl
import com.elfeky.data.repo.PinRepoImpl
import com.elfeky.data.repo.ProjectRepoImpl
import com.elfeky.data.repo.SprintIssueRepoImpl
import com.elfeky.data.repo.SprintRepoImpl
import com.elfeky.data.repo.TenantRepoImpl
import com.elfeky.domain.repo.AssignUserIssueRepo
import com.elfeky.domain.repo.AuthenticationRepo
import com.elfeky.domain.repo.BacklogRepo
import com.elfeky.domain.repo.DashBoardRepo
import com.elfeky.domain.repo.IssueRepo
import com.elfeky.domain.repo.PinRepo
import com.elfeky.domain.repo.ProjectRepo
import com.elfeky.domain.repo.SprintIssueRepo
import com.elfeky.domain.repo.SprintRepo
import com.elfeky.domain.repo.TenantRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {
    @Binds
    @Singleton
    abstract fun bindAuthenticationRepo(
        authenticationRepoImpl: AuthenticationRepoImpl
    ): AuthenticationRepo

    @Binds
    @Singleton
    abstract fun bindTenantRepo(
        tenantRepoImpl: TenantRepoImpl
    ): TenantRepo

    @Binds
    @Singleton
    abstract fun bindProjectRepo(
        projectRepoImpl: ProjectRepoImpl
    ): ProjectRepo

    @Binds
    @Singleton
    abstract fun bindPinRepo(
        pinRepoImpl: PinRepoImpl
    ): PinRepo

    @Binds
    @Singleton
    abstract fun bindDashBoardRepo(
        dashBoardRepoImpl: DashBoardRepoImpl
    ): DashBoardRepo

    @Binds
    @Singleton
    abstract fun bindBacklogRepo(
        backlogRepoImpl: BacklogRepoImpl
    ): BacklogRepo


    @Binds
    @Singleton
    abstract fun bindSprintRepo(
        sprintRepoImpl: SprintRepoImpl
    ): SprintRepo

    @Binds
    @Singleton
    abstract fun bindIssueRepo(
        issueRepoImpl: IssueRepoImpl
    ): IssueRepo

    @Binds
    @Singleton
    abstract fun bindSprintIssueRepo(
        sprintIssueRepoImpl: SprintIssueRepoImpl
    ): SprintIssueRepo

    @Binds
    @Singleton
    abstract fun bindAssignUserIssueRepo(
        assignUserIssueRepoImpl: AssignUserIssueRepoImpl
    ): AssignUserIssueRepo

    @Binds
    @Singleton
    abstract fun bindJoinRepo(
        assignUserIssueRepoImpl: AssignUserIssueRepoImpl
    ): AssignUserIssueRepo

    @Binds
    @Singleton
    abstract fun bindCommentRepo(
        assignUserIssueRepoImpl: AssignUserIssueRepoImpl
    ): AssignUserIssueRepo

    @Binds
    @Singleton
    abstract fun bindNotificationRepo(
        assignUserIssueRepoImpl: AssignUserIssueRepoImpl
    ): AssignUserIssueRepo

}