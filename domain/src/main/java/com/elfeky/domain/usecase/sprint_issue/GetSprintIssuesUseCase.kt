package com.elfeky.domain.usecase.sprint_issue

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.pager.CustomPagerSource
import com.elfeky.domain.repo.SprintIssueRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSprintIssuesUseCase @Inject constructor(
    private val repo: SprintIssueRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
) {
    operator fun invoke(projectId: Int): Flow<PagingData<Issue>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CustomPagerSource {
                    repo.getIssues(
                        accessToken = accessTokenUseCase.get(),
                        sprintId = projectId,
                        pageNumber = it,
                        pageSize = 20
                    )
                }
            }
        ).flow
    }
}