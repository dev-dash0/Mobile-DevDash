package com.elfeky.domain.usecase.comment

import com.elfeky.domain.model.comment.Comment
import com.elfeky.domain.repo.CommentRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SendCommentUseCase @Inject constructor(
    private val repo: CommentRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(issueId: Int, content: String): Flow<Resource<Comment>> = flow {
        try {
            emit(Resource.Loading())
            val comment = repo.sendComment(accessTokenUseCase.get(), issueId, content)
            emit(Resource.Success(comment))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "Unexpected error occurred"))
        }
    }
}