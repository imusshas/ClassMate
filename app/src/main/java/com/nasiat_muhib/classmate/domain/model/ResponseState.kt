package com.nasiat_muhib.classmate.domain.model

sealed class ResponseState<out T>(
    val data: T? = null,
    val error: String? = null
) {
    data object Loading: ResponseState<Nothing>()
    data class Success<out T>(val info: T? = null): ResponseState<T>(data = info)
    data class Failure<out E>(val message: String): ResponseState<E>(error = message)
}