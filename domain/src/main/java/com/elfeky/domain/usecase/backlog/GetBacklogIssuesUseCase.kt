package com.elfeky.domain.usecase.backlog

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elfeky.domain.model.issue.Issue
import com.elfeky.domain.pager.CustomPagerSource
import com.elfeky.domain.repo.BacklogRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBacklogIssuesUseCase @Inject constructor(
    private val repo: BacklogRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
) {
    operator fun invoke(projectId: Int): Flow<PagingData<Issue>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            initialKey = 1,
            pagingSourceFactory = {
                CustomPagerSource {
                    repo.getIssues(
                        accessToken = accessTokenUseCase.get(),
                        projectId = projectId,
                        pageNumber = it,
                        pageSize = 20
                    )
                }
            }
        ).flow
    }
}