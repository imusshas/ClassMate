package com.nasiat_muhib.classmate.domain.event

sealed class CreateAssignmentUIEvent {
    data class AssignmentClassroomChanged(val classroom: String): CreateAssignmentUIEvent()
    data class AssignmentDayChanged(val day: String): CreateAssignmentUIEvent()
    data class AssignmentMonthChanged(val month: String): CreateAssignmentUIEvent()
    data class AssignmentYearChanged(val year: String): CreateAssignmentUIEvent()
    data class AssignmentHourChanged(val hour: String): CreateAssignmentUIEvent()
    data class AssignmentMinuteChanged(val minute: String): CreateAssignmentUIEvent()
    data class AssignmentShiftChanged(val shift: String): CreateAssignmentUIEvent()
    data object AssignmentCancelButtonClick: CreateAssignmentUIEvent()
    data object AssignmentCreateButtonClick: CreateAssignmentUIEvent()
}