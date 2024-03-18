package com.nasiat_muhib.classmate.domain.event

sealed class CreateResourceUIEvent {

    data class TitleChanged(val title: String): CreateResourceUIEvent()
    data class LinkChanged(val link: String): CreateResourceUIEvent()

    data object CreateButtonClicked: CreateResourceUIEvent()
    data object CancelButtonClicked: CreateResourceUIEvent()
}