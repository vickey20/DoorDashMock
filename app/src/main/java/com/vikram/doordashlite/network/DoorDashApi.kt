package com.vikram.doordashlite.network

import com.vikram.doordashlite.BuildConfig.RESTAURANT_DETAIL
import com.vikram.doordashlite.BuildConfig.STORE_FEED
import com.vikram.doordashlite.model.Store
import com.vikram.doordashlite.model.StoreDetail
import com.vikram.doordashlite.model.StoreFeed
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *   Created by vikram.gupta on 4/4/21.
 */
interface DoorDashApi {
    @GET(STORE_FEED)
    fun getStoreFeed(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<StoreFeed>

    @GET("$RESTAURANT_DETAIL/{id}")
    fun getStoreDetail(
        @Path("id") id: Int
    ): Single<StoreDetail>
}