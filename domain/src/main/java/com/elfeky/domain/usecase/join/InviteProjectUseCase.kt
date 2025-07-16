package com.elfeky.domain.usecase.join

import com.elfeky.domain.model.error.InviteError
import com.elfeky.domain.model.join.InviteProjectRequest
import com.elfeky.domain.repo.InviteProjectRepo
import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
import com.elfeky.domain.util.Resource
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class InviteProjectUseCase @Inject constructor(
    private val repo: InviteProjectRepo,
    private val accessTokenUseCase: AccessTokenUseCase
) {
    operator fun invoke(request: InviteProjectRequest): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            repo.invite(accessTokenUseCase.get(), request)
            emit(Resource.Success())
        } catch (_: IOException) {
            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection."))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()

            val errorMessage: String = if (errorBody != null) {
                try {
                    val inviteError = Gson().fromJson(errorBody, InviteError::class.java)

                    if (!inviteError.success && inviteError.message.isNotEmpty()) {
                        inviteError.message
                    } else if (inviteError.message.isNotEmpty()) {
                        inviteError.message
                    } else {
                        "Server responded with an error (Code: ${e.code()})."
                    }
                } catch (_: JsonSyntaxException) {
                    "An unexpected server response format was received (Code: ${e.code()})."
                } catch (_: Exception) {
                    "An unexpected error occurred while processing server response (Code: ${e.code()})."
                }
            } else {
                "Server returned an error with no additional details (Code: ${e.code()})."
            }
            emit(Resource.Error(message = errorMessage))
        } catch (_: Exception) {
            emit(Resource.Error(message = "An unexpected error occurred."))
        }
    }
}