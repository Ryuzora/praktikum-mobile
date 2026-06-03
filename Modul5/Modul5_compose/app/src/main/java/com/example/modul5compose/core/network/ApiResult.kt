package com.example.modul5compose.core.network

sealed class ApiResult<out T> {
    data object Loading : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : ApiResult<Nothing>()
}
