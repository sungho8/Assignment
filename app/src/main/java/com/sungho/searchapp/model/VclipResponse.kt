package com.sungho.searchapp.model

import com.google.gson.annotations.SerializedName


data class KakaoVclip(
    val thumbnail : String,
    val datetime : String
)

data class VclipSearchResponse(
    @SerializedName("meta")
    val metaData: MetaData?,

    @SerializedName("documents")
    var documents: MutableList<KakaoVclip>?
)
