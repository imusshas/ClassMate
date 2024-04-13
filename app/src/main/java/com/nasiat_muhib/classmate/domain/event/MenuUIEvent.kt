package com.nasiat_muhib.classmate.domain.event

sealed class MenuUIEvent {
    data object SignOutButtonClicked: MenuUIEvent()
}