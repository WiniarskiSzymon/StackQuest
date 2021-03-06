package com.example.swierk.stackquest.model



import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Question(
    @Json(name = "accepted_answer_id")
    val acceptedAnswerId: Int?,
    @Json(name = "answer_count")
    val answerCount: Int,
    @Json(name = "bounty_amount")
    val bountyAmount: Int?,
    @Json(name = "bounty_closes_date")
    val bountyClosesDate: Int?,
    @Json(name = "closed_date")
    val closedDate: Int?,
    @Json(name = "closed_reason")
    val closedReason: String?,
    @Json(name = "creation_date")
    val creationDate: Int,
    @Json(name = "is_answered")
    val isAnswered: Boolean,
    @Json(name = "last_activity_date")
    val lastActivityDate: Int,
    @Json(name = "last_edit_date")
    val lastEditDate: Int?,
    @Json(name = "link")
    val link: String,
    @Json(name = "owner")
    val owner: Owner,
    @Json(name = "question_id")
    val questionId: Int,
    @Json(name = "score")
    val score: Int,
    @Json(name = "tags")
    val tags: List<String>,
    @Json(name = "title")
    val title: String,
    @Json(name = "view_count")
    val viewCount: Int
)