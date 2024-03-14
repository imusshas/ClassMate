package com.nasiat_muhib.classmate.domain.event

sealed class CreateTermTestUIEvent {
    data class TermTestClassroomChanged(val classroom: String): CreateTermTestUIEvent()
    data class TermTestDayChanged(val day: String): CreateTermTestUIEvent()
    data class TermTestMonthChanged(val month: String): CreateTermTestUIEvent()
    data class TermTestYearChanged(val year: String): CreateTermTestUIEvent()
    data class TermTestHourChanged(val hour: String): CreateTermTestUIEvent()
    data class TermTestMinuteChanged(val minute: String): CreateTermTestUIEvent()
    data class TermTestShiftChanged(val shift: String): CreateTermTestUIEvent()
    data object TermTestCancelButtonClick: CreateTermTestUIEvent()
    data object TermTestCreateButtonClick: CreateTermTestUIEvent()
}