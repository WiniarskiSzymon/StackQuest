package com.example.swierk.stackquest.model

class Response (val status: Status, val errorMessage : String? = "")
enum class Status {
    SUCCESS, ERROR, LOADING
}