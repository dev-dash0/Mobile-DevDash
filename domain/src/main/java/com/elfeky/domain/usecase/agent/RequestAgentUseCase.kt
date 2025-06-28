//package com.elfeky.domain.usecase.agent
//
//import com.elfeky.domain.model.agent.AgentResponse
//import com.elfeky.domain.repo.AgentRepo
//import com.elfeky.domain.usecase.local_storage.AccessTokenUseCase
//import com.elfeky.domain.util.Resource
//import com.google.gson.Gson
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.flow
//import retrofit2.HttpException
//import java.io.IOException
//import javax.inject.Inject
//
//class RequestAgentUseCase @Inject constructor(
//    private val repo: AgentRepo,
//    private val accessTokenUseCase: AccessTokenUseCase,
//    private val gson: Gson
//)
//{
//    operator  fun invoke(text: String, startDate: String, endDate: String, tenantId: String):Flow<Resource<String>> = flow {
//        try {
//            emit(Resource.Loading())
//             repo.startSseStream(
//                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6InNhbXkxMiIsImp0aSI6IkpUSWYyYzI1ZjZjLTI5MGYtNDFhZS1hM2YxLTk0ZTg1NDEwZWVlZSIsImVtYWlsIjoic2FteUBnbWFpbC5jb20iLCJzdWIiOiI4MyIsIm5iZiI6MTc1MTE0MzQ3NSwiZXhwIjoxNzUxMTg2Njc1LCJpYXQiOjE3NTExNDM0NzUsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6NDQzMDYiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjQyMDAifQ.taGd4cgBhQQ1Yt1o0-i3H-GlxIbnjEY5uMKmlv3rjR0",
//                text = text,
//                startDate = startDate,
//                endDate = endDate,
//                tenantId = tenantId,
//                onEvent = { event->
//                    val response = gson.fromJson(event, AgentResponse::class.java)
//                    when (response.type) {
//                        "start" ->  emit(Resource.Success(data ="Chat started: ${response.chat_id}" ))
//                        "token" -> emit(Resource.Success(data = "${response.content}"))
//                        "tool_call" -> emit(Resource.Success(data = "Tool: ${response.tool_name}"))
//                        "tool_output" -> emit(Resource.Success(data = "Tool Output (${response.tool_name}): ${response.output}"))
//                        "end" ->emit(Resource.Success(data =  "Chat ended: ${response.chat_id}"))
//                        "error" -> emit(Resource.Success(data = "Error: ${response.content}"))
//                        else -> emit(Resource.Success(data = "Unknown event type: $event"))
//                    }
//                },
//                onError = { error->
//                    emit(Resource.Error(message = "Error: ${error.message}"))
//                },
//                onComplete = {
//                    emit(Resource.Success(data = "Stream completed."))
//                }
//            )
//
//        } catch (e: IOException) {
//            emit(Resource.Error(message = "Couldn't reach server. Check your internet connection"))
//        } catch (e: HttpException) {
//                emit(Resource.Error(message = "Unexpected error occurred"))
//        }
//    }
//    }
