package com.example.swierk.stackquest.model

class Response private constructor(val status: Response.Status, val data: QueryResult?, val errorMessage : String?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }
    companion object {
        fun success(data: QueryResult?): Response {
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