package com.example.swierk.stackquest.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("has_more")
    val hasMore: Boolean,
    @SerializedName("items")
    val items: List<Question>,
    @SerializedName("quota_max")
    val quotaMax: Int,
    @SerializedName("quota_remaining")
    val quotaRemaining: Int
)