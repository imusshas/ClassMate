package com.nasiat_muhib.classmate.domain.event

sealed class CreateClassUIEvent {

    data class WeekDayChanged(val  weekDay: String): CreateClassUIEvent()
    data class ClassRoomChanged(val  classroom: String): CreateClassUIEvent()
    data class SectionChanged(val  section: String): CreateClassUIEvent()
    data class StartHourChanged(val  startHour: String): CreateClassUIEvent()
    data class StartMinuteChanged(val  startMinute: String): CreateClassUIEvent()
    data class StartShiftChanged(val  startShift: String): CreateClassUIEvent()
    data class EndHourChanged(val  endHour: String): CreateClassUIEvent()
    data class EndMinuteChanged(val  endMinute: String): CreateClassUIEvent()
    data class EndShiftChanged(val  endShift: String): CreateClassUIEvent()

    data object CreateButtonClick: CreateClassUIEvent()
    data object CancelButtonClick: CreateClassUIEvent()
}