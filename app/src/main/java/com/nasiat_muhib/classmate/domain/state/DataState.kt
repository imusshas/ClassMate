package com.nasiat_muhib.classmate.domain.state

sealed class DataState<out T> (
    val data: T? = null,
    val error: String? = null
){

    data object Loading : DataState<Nothing>()
    data class Success <out T> (val info: T): DataState<T>(data = info)
    data class Error <out T> (val message: String): DataState<T>(error = message)
}