package com.nasiat_muhib.classmate.domain.event

import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.navigation.Screen

sealed class EnrollCourseUIEvent {
    data class DisplayCourseSwipe(val course: Course, val screen: Screen): EnrollCourseUIEvent()

    data class LeaveCourseSwipe(val courseId: String): EnrollCourseUIEvent()
}