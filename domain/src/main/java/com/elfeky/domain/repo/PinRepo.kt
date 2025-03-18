package com.elfeky.domain.repo

import com.elfeky.domain.model.pin.PinnedItems

interface PinRepo {
    suspend fun pinItem(accessToken: String, id: Int, type: String)
    suspend fun getPinnedItems(accessToken: String): PinnedItems
    suspend fun unpinItem(accessToken: String, id: Int, type: String)
}