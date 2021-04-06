package com.vikram.doordashlite.model

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso

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
    val status: Status
)

data class Status(
    @SerializedName("asap_minutes_range")
    val minutesRange: List<Int>,
) {
    fun getEta(): String {
        return "${minutesRange[0]} Mins"
    }
}

@BindingAdapter("url", "loading", "error")
fun loadImage(view: ImageView, url: String, loading: Drawable, error: Drawable) {
    Picasso.get()
            .load(url)
            .placeholder(loading)
            .error(error)
            .into(view)
}