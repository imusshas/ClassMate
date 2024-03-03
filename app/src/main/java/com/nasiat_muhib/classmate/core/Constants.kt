package com.nasiat_muhib.classmate.core

import com.nasiat_muhib.classmate.strings.CLASS_REPRESENTATIVE
import com.nasiat_muhib.classmate.strings.FIRST_YEAR_FIRST_SEMESTER
import com.nasiat_muhib.classmate.strings.FIRST_YEAR_SECOND_SEMESTER
import com.nasiat_muhib.classmate.strings.FOURTH_YEAR_FIRST_SEMESTER
import com.nasiat_muhib.classmate.strings.FOURTH_YEAR_SECOND_SEMESTER
import com.nasiat_muhib.classmate.strings.FRIDAY
import com.nasiat_muhib.classmate.strings.MONDAY
import com.nasiat_muhib.classmate.strings.SATURDAY
import com.nasiat_muhib.classmate.strings.SECOND_YEAR_FIRST_SEMESTER
import com.nasiat_muhib.classmate.strings.SECOND_YEAR_SECOND_SEMESTER
import com.nasiat_muhib.classmate.strings.STUDENT
import com.nasiat_muhib.classmate.strings.SUNDAY
import com.nasiat_muhib.classmate.strings.TEACHER
import com.nasiat_muhib.classmate.strings.THIRD_YEAR_FIRST_SEMESTER
import com.nasiat_muhib.classmate.strings.THIRD_YEAR_SECOND_SEMESTER
import com.nasiat_muhib.classmate.strings.THURSDAY
import com.nasiat_muhib.classmate.strings.TUESDAY
import com.nasiat_muhib.classmate.strings.WEDNESDAY

object Constants {

    val ROLES = listOf(TEACHER, CLASS_REPRESENTATIVE, STUDENT)
    val CHANGEABLE_ROLES = listOf(CLASS_REPRESENTATIVE, STUDENT)
    val WEEK_DAYS = listOf(
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY
    )

    val SEMESTERS = listOf(
        FIRST_YEAR_FIRST_SEMESTER,
        FIRST_YEAR_SECOND_SEMESTER,
        SECOND_YEAR_FIRST_SEMESTER,
        SECOND_YEAR_SECOND_SEMESTER,
        THIRD_YEAR_FIRST_SEMESTER,
        THIRD_YEAR_SECOND_SEMESTER,
        FOURTH_YEAR_FIRST_SEMESTER,
        FOURTH_YEAR_SECOND_SEMESTER
    )
}