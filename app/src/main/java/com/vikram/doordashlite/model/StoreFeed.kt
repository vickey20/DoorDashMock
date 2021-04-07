package com.vikram.doordashlite.model

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import com.vikram.doordashlite.R
import java.text.DecimalFormat

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
    val coverImgUrl: String,
    @SerializedName("header_img_url")
    val headerImgUrl: String?,
    val status: Status,
    val menus: List<StoreMenu>,
    @SerializedName("display_delivery_fee")
    val deliveryFee: String,
    @SerializedName("average_rating")
    val averageRating: Double,
    @SerializedName("num_ratings")
    val numRatings: Int,
    @SerializedName("distance_from_consumer")
    val distance: Double
) {
    fun getDistanceString(): String {
        val df = DecimalFormat("#.#")
        return df.format(distance)
    }
}

data class StoreMenu (
    @SerializedName("popular_items")
    val popularItems: List<StoreMenuItem>,
    val name: String,
    val subtitle: String
)

data class StoreMenuItem (
    val price: Int,
    @SerializedName("img_url")
    val imgUrl: String,
    val description: String,
    val name: String,
    val id: Long
)

data class Status(
    @SerializedName("asap_minutes_range")
    val minutesRange: List<Int>,
    @SerializedName("pickup_available")
    val pickupAvailable: Boolean
) {
    fun getEta(): String {
        return "${minutesRange[0]} Mins"
    }
}

private val priceRangeMap = mapOf(1 to "$", 2 to "$$", 3 to "$$$", 4 to "$$$$")

data class StoreDetail(
    val id: Int,
    var name: String,
    val description: String,
    @SerializedName("cover_img_url")
    val coverImgUrl: String,
    @SerializedName("delivery_fee")
    val deliveryFee: String,
    @SerializedName("price_range")
    val priceRange: Int,
    @SerializedName("header_img_url")
    var headerImgUrl: String?,
    var menus: List<StoreMenu>,
    @SerializedName("average_rating")
    val averageRating: Double,
    @SerializedName("number_of_ratings")
    val numRatings: Int,
    @SerializedName("offers_pickup")
    val offersPickup: Boolean,
    var distance: String
) {
    fun getPriceRangeSymbol() = priceRangeMap[priceRange]
    fun getAverageRating() = averageRating.toString()
}

@BindingAdapter("url", "loading", "error")
fun loadImage(view: ImageView, url: String?, loading: Drawable, error: Drawable) {
    url?.let {
        Picasso.get()
            .load(it)
            .placeholder(loading)
            .error(error)
            .into(view)
    } ?: Picasso.get()
        .load(R.drawable.broken_image)
        .placeholder(loading)
        .error(error)
        .into(view)
}