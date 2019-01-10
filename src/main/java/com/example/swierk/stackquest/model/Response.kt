package com.example.swierk.stackquest.model

class Response private constructor(val status: Response.Status, val data: List<Question>?, val errorMessage : String?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }
    companion object {
        fun success(data: List<Question>?): Response {
            return Response(Status.SUCCESS, data, null)
        }
        fun error( errorMessage: String?): Response {
            return Response(Status.ERROR, null, errorMessage)
        }
        fun loading(): Response {
            return Response(Status.LOADING, null, null)
        }
    }
}