package com.nasiat_muhib.classmate.domain.model

sealed class DataOrException<out T>(
    val data: T? =null,
    val error: String? = null
) {
    data class Data<out T>(val info: T? = null): DataOrException<T>(data = info)
    data class Exception(val errorMessage: String? = null): DataOrException<Nothing>(error = errorMessage)
}
