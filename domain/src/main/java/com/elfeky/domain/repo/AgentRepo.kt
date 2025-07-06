package com.elfeky.domain.repo

interface AgentRepo {
     fun startSseStream(
        text: String,
        startDate: String,
        endDate: String,
        tenantId: String,
        token: String,
        onEvent:  (String) -> Unit,
        onError:  (Throwable) -> Unit,
        onComplete:  () -> Unit
    )

     fun stopSseStream()
}