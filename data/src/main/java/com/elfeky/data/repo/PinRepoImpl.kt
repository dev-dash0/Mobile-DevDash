package com.elfeky.data.repo

import com.elfeky.data.remote.PinApiService
import com.elfeky.domain.model.pin.PinnedItems
import com.elfeky.domain.repo.PinRepo

class PinRepoImpl(private val pinApiService: PinApiService) : PinRepo {

    override suspend fun pinItem(accessToken: String, id: Int, type: String) =
        pinApiService.pinItem("Bearer $accessToken", id, type)

    override suspend fun getPinnedItems(accessToken: String): PinnedItems =
        pinApiService.getPinnedItems("Bearer $accessToken").result

    override suspend fun unpinItem(accessToken: String, id: Int, type: String) =
        pinApiService.unpinItem("Bearer $accessToken", id, type)
}