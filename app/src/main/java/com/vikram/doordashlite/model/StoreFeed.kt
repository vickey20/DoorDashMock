package com.vikram.doordashlite.model

import com.google.gson.annotations.SerializedName

/**
 *   Created by vikram.gupta on 4/4/21.
 */
data class StoreFeed(
    val stores: List<Store>
)

data class Store(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("cover_img_url")
    val coverImgUrl: String
)
