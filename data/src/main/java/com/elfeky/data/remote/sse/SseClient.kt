package com.elfeky.data.remote.sse

import okhttp3.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class SseClient {
    private var call: Call? = null
    private val client = OkHttpClient()

    fun start(
        url: String,
        requestBody: RequestBody,
        token: String,
        onEvent:  (String) -> Unit,
        onError:  (Throwable) -> Unit,
        onComplete:  () -> Unit
    ) {
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .header("Accept", "text/event-stream")
            .addHeader("Authorization", "Bearer $token")
            .build()

        call = client.newCall(request)

        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val reader = BufferedReader(InputStreamReader(response.body?.byteStream()))
                    var line: String?
                    val dataBuilder = StringBuilder()

                    while (reader.readLine().also { line = it } != null) {
                        if (line!!.startsWith("data:")) {
                            dataBuilder.append(line.removePrefix("data:").trim())
                        } else if (line.isBlank()) {
                            onEvent(dataBuilder.toString())
                            dataBuilder.clear()
                        }
                    }

                    onComplete()
                } catch (e: Exception) {
                    onError(e)
                }
            }
        })
    }

    fun stop() {
        call?.cancel()
    }
}