package com.elfeky.domain.usecase.comment

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elfeky.domain.model.comment.Comment
import com.elfeky.domain.pager.CustomPagerSource
import com.elfeky.domain.repo.CommentRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repo: CommentRepo,
    private val accessTokenUseCase: AccessTokenUseCase,
) {
    operator fun invoke(issueId: Int): Flow<PagingData<Comment>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            initialKey = 1,
            pagingSourceFactory = {
                CustomPagerSource {
                    repo.getComments(
                        accessToken = accessTokenUseCase.get(),
                        issueId = issueId,
                        pageNumber = it,
                        pageSize = 20
                    )
                }
            }
        ).flow
    }
}