package com.example.swierk.stackquest.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QueryResult(
    @Json(name = "has_more")
    val hasMore: Boolean,
    @Json(name = "items")
    val items: List<Question>,
    @Json(name = "quota_max")
    val quotaMax: Int,
    @Json(name = "quota_remaining")
    val quotaRemaining: Int
)