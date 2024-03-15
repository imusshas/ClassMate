package com.nasiat_muhib.classmate.domain.event

sealed class CreateAssignmentUIEvent {
    data class ClassroomChanged(val classroom: String): CreateAssignmentUIEvent()
    data class DayChanged(val day: String): CreateAssignmentUIEvent()
    data class MonthChanged(val month: String): CreateAssignmentUIEvent()
    data class YearChanged(val year: String): CreateAssignmentUIEvent()
    data class HourChanged(val hour: String): CreateAssignmentUIEvent()
    data class MinuteChanged(val minute: String): CreateAssignmentUIEvent()
    data class ShiftChanged(val shift: String): CreateAssignmentUIEvent()
    data object CancelButtonClick: CreateAssignmentUIEvent()
    data object CreateButtonClick: CreateAssignmentUIEvent()
}