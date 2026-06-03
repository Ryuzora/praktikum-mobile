package com.example.modul5compose.core.network

import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (exception: IOException) {
        ApiResult.Error(
            message = "Tidak ada koneksi internet. Periksa jaringan kamu.",
            throwable = exception
        )
    } catch (exception: HttpException) {
        ApiResult.Error(
            message = "Terjadi kesalahan server: ${exception.code()}",
            throwable = exception
        )
    } catch (exception: Exception) {
        ApiResult.Error(
            message = exception.message ?: "Terjadi kesalahan tidak diketahui.",
            throwable = exception
        )
    }
}
