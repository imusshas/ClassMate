package com.nasiat_muhib.classmate.domain.event

import com.nasiat_muhib.classmate.data.model.ClassDetails

sealed class HomeUIEvent {
    data object TodayClassClicked: HomeUIEvent()
    data object TomorrowClassClicked: HomeUIEvent()

    data class ClassStatusChange(val classDetails: ClassDetails, val isActive: Boolean): HomeUIEvent()
}