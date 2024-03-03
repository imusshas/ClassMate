package com.nasiat_muhib.classmate.domain.event

sealed class CreateSemesterUIEvent {
    data object CreateSemesterFABClick: CreateSemesterUIEvent()
}