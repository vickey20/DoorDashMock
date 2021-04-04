package com.vikram.doordashlite.repo

import com.vikram.doordashlite.model.StoreFeed
import com.vikram.doordashlite.network.DoorDashApi
import io.reactivex.rxjava3.core.Single

/**
 *   Created by vikram.gupta on 4/4/21.
 */
class DoorDashRepository(private val api: DoorDashApi) {
    companion object {
        private const val DEFAULT_LAT = 37.422740
        private const val DEFAULT_LNG = 37.422740
        private const val DEFAULT_OFFSET = 0
        private const val DEFAULT_LIMIT = 50
    }

    fun getStoreFeed(
        lat: Double = DEFAULT_LAT,
        lng: Double = DEFAULT_LNG,
        offset: Int = DEFAULT_OFFSET,
        limit: Int = DEFAULT_LIMIT
    ): Single<StoreFeed> {
        return api.getStoreFeed(lat, lng, offset, limit)
    }
}