package com.sungho.searchapp.model

import com.google.gson.annotations.SerializedName

data class KakaoImage(
    @SerializedName("display_sitename")
    val sitename : String,
    val collection : String,
    val image_url : String
)

data class MetaData(
    @SerializedName("total_count")
    val totalCount: Int?,

    @SerializedName("is_end")
    val isEnd: Boolean?
)

data class ImageSearchResponse(
    @SerializedName("meta")
    val metaData: MetaData?,

    @SerializedName("documents")
    var documents: MutableList<KakaoImage>?
)
