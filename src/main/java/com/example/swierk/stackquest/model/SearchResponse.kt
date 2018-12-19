package com.example.swierk.stackquest.model


import com.squareup.moshi.Json

data class SearchResponse(
    @Json(name = "has_more")
    val hasMore: Boolean,
    @Json(name = "items")
    val items: List<Question>,
    @Json(name = "quota_max")
    val quotaMax: Int,
    @Json(name = "quota_remaining")
    val quotaRemaining: Int
)