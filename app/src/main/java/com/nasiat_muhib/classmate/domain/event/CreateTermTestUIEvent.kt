package com.nasiat_muhib.classmate.domain.event

sealed class CreateTermTestUIEvent {
    data class ClassroomChanged(val classroom: String): CreateTermTestUIEvent()
    data class DayChanged(val day: String): CreateTermTestUIEvent()
    data class MonthChanged(val month: String): CreateTermTestUIEvent()
    data class YearChanged(val year: String): CreateTermTestUIEvent()
    data class HourChanged(val hour: String): CreateTermTestUIEvent()
    data class MinuteChanged(val minute: String): CreateTermTestUIEvent()
    data class ShiftChanged(val shift: String): CreateTermTestUIEvent()
    data object CancelButtonClick: CreateTermTestUIEvent()
    data object CreateButtonClick: CreateTermTestUIEvent()
}