package com.nasiat_muhib.classmate.domain.event

sealed class DisplayCourseDetailsUIEvent {

    //

    // Term Test
    data class TermTestEventTypeChanged(val type: String): DisplayCourseDetailsUIEvent()
    data class TermTestClassroomChanged(val classroom: String): DisplayCourseDetailsUIEvent()
    data class TermTestDayChanged(val day: Int): DisplayCourseDetailsUIEvent()
    data class TermTestMonthChanged(val month: String): DisplayCourseDetailsUIEvent()
    data class TermTestYearChanged(val year: Int): DisplayCourseDetailsUIEvent()
    data class TermTestHourChanged(val hour: Int): DisplayCourseDetailsUIEvent()
    data class TermTestMinuteChanged(val minute: Int): DisplayCourseDetailsUIEvent()
    data class TermTestShiftChanged(val shift: Int): DisplayCourseDetailsUIEvent()
    data object TermTestCancelButtonClick: DisplayCourseDetailsUIEvent()
    data object TermTestCreateButtonClick: DisplayCourseDetailsUIEvent()


    // Assignment
    data class AssignmentEventTypeChanged(val type: String): DisplayCourseDetailsUIEvent()
    data class AssignmentClassroomChanged(val classroom: String): DisplayCourseDetailsUIEvent()
    data class AssignmentDayChanged(val day: Int): DisplayCourseDetailsUIEvent()
    data class AssignmentMonthChanged(val month: String): DisplayCourseDetailsUIEvent()
    data class AssignmentYearChanged(val year: Int): DisplayCourseDetailsUIEvent()
    data class AssignmentHourChanged(val hour: Int): DisplayCourseDetailsUIEvent()
    data class AssignmentMinuteChanged(val minute: Int): DisplayCourseDetailsUIEvent()
    data class AssignmentShiftChanged(val shift: Int): DisplayCourseDetailsUIEvent()
    data object AssignmentCancelButtonClick: DisplayCourseDetailsUIEvent()
    data object AssignmentCreateButtonClick: DisplayCourseDetailsUIEvent()
}