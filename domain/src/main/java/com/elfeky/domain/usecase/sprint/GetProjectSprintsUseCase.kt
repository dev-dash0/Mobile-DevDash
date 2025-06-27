package com.elfeky.domain.usecase.sprint

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elfeky.domain.model.sprint.Sprint
import com.elfeky.domain.pager.CustomPagerSource
import com.elfeky.domain.repo.SprintRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProjectSprintsUseCase @Inject constructor(
    private val repo: SprintRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
) {
    operator fun invoke(projectId: Int): Flow<PagingData<Sprint>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CustomPagerSource {
                    repo.getProjectSprints(
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