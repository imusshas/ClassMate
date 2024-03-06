package com.nasiat_muhib.classmate.domain.event

sealed class SearchUIEvent {
    data class SearchTextChanged(val searchText: String): SearchUIEvent()
}