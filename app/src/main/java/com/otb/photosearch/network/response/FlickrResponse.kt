package com.otb.photosearch.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohit Rajput on 27/9/20.
 */
data class FlickrResponse(
    @SerializedName("stat")
    val status: String,

    @SerializedName("photos")
    val photos: PhotoData
)

data class PhotoData(
    @SerializedName("page")
    val page: Int,

    @SerializedName("pages")
    val pages: Int,

    @SerializedName("perpage")
    val perpage: Int,

    @SerializedName("total")
    val total: Int,

    @SerializedName("photo")
    val photos: List<FlickrPhoto>
)

data class FlickrPhoto(
    @SerializedName("id")
    val id: String,

    @SerializedName("owner")
    val owner: String,

    @SerializedName("secret")
    val secret: String,

    @SerializedName("server")
    val server: String,

    @SerializedName("farm")
    val farm: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("ispublic")
    val isPublic: Int,

    @SerializedName("isfriend")
    val isFriend: Int,

    @SerializedName("isfamily")
    val isFamily: Int
)