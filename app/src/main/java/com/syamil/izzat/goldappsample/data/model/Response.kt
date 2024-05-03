package com.syamil.izzat.goldappsample.data.model

sealed class Response<T> constructor(val code: Int? = 500, val message: String? = "", val data: T? = null) {
    class Idle<T> : Response<T>()
    class Loading<T> : Response<T>()
    class Success<T> constructor(data: T?) : Response<T>(data = data)
    class Error<T> : Response<T>(message = "Error")
    class Unauthorised<T> : Response<T>(code = 401)
}

open class BaseResponse(open val code: Int, open val message: String)