package com.sungho.searchapp.model

import com.google.gson.annotations.SerializedName

data class KakaoImage(
    val display_sitename : String,
    val thumbnail_url : String,
    val datetime : String,
)

data class ImageSearchResponse(
    @SerializedName("meta")
    val metaData: MetaData?,

    @SerializedName("documents")
    var documents: MutableList<KakaoImage>?
)
