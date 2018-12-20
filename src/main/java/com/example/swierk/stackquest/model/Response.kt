package com.example.swierk.stackquest.model

class Response private constructor(val status: Response.Status, val data: QueryResult?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }
    companion object {
        fun success(data: QueryResult): Response {
            return Response(Status.SUCCESS, data)
        }
        fun error(): Response {
            return Response(Status.ERROR, null)
        }
        fun loading(): Response {
            return Response(Status.LOADING, null)
        }
    }
}