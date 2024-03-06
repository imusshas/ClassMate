package com.nasiat_muhib.classmate.core

import com.google.firebase.firestore.DocumentSnapshot
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.strings.BLOOD_GROUP
import com.nasiat_muhib.classmate.strings.CLASSROOM
import com.nasiat_muhib.classmate.strings.COURSES
import com.nasiat_muhib.classmate.strings.COURSE_CODE
import com.nasiat_muhib.classmate.strings.COURSE_CREATOR
import com.nasiat_muhib.classmate.strings.COURSE_CREDIT
import com.nasiat_muhib.classmate.strings.COURSE_DEPARTMENT
import com.nasiat_muhib.classmate.strings.COURSE_SEMESTER
import com.nasiat_muhib.classmate.strings.COURSE_TEACHER
import com.nasiat_muhib.classmate.strings.COURSE_TITLE
import com.nasiat_muhib.classmate.strings.DEPARTMENT
import com.nasiat_muhib.classmate.strings.EMAIL
import com.nasiat_muhib.classmate.strings.END_HOUR
import com.nasiat_muhib.classmate.strings.END_MINUTE
import com.nasiat_muhib.classmate.strings.END_SHIFT
import com.nasiat_muhib.classmate.strings.FIRST_NAME
import com.nasiat_muhib.classmate.strings.LAST_NAME
import com.nasiat_muhib.classmate.strings.PHONE_NO
import com.nasiat_muhib.classmate.strings.REQUESTED_COURSES
import com.nasiat_muhib.classmate.strings.ROLE
import com.nasiat_muhib.classmate.strings.SECTION
import com.nasiat_muhib.classmate.strings.SESSION
import com.nasiat_muhib.classmate.strings.START_HOUR
import com.nasiat_muhib.classmate.strings.START_MINUTE
import com.nasiat_muhib.classmate.strings.START_SHIFT
import com.nasiat_muhib.classmate.strings.WEEKDAY

object GetModelFromDocument {
    fun getUserFromFirestoreDocument(snapshot: DocumentSnapshot): User {

        val firstName = if (snapshot[FIRST_NAME] != null) snapshot[FIRST_NAME] as String else ""
        val lastName = if (snapshot[LAST_NAME] != null) snapshot[LAST_NAME] as String else ""
        val role = if (snapshot[ROLE] != null) snapshot[ROLE] as String else ""
        val department = if (snapshot[DEPARTMENT] != null) snapshot[DEPARTMENT] as String else ""
        val session = if (snapshot[SESSION] != null) snapshot[SESSION] as String else ""
        val bloodGroup = if (snapshot[BLOOD_GROUP] != null) snapshot[BLOOD_GROUP] as String else ""
        val phoneNo = if (snapshot[PHONE_NO] != null) snapshot[PHONE_NO] as String else ""
        val email = if (snapshot[EMAIL] != null) snapshot[EMAIL] as String else ""
        val courses = if (snapshot[COURSES] != null) snapshot[COURSES] as List<String> else emptyList()
        val requestedCourses = if (snapshot[REQUESTED_COURSES] != null) snapshot[REQUESTED_COURSES] as List<String> else emptyList()

//        val user = User(
//            firstName = firstName,
//            lastName = lastName,
//            role = role,
//            department = department,
//            session = session,
//            bloodGroup = bloodGroup,
//            phoneNo = phoneNo,
//            email = email,
//            courses = courses,
//            requestedCourses = requestedCourses
//        )
//        Log.d(TAG, "getUserFromFirestoreDocument: $user")

        return User(
            firstName = firstName,
            lastName = lastName,
            role = role,
            department = department,
            session = session,
            bloodGroup = bloodGroup,
            phoneNo = phoneNo,
            email = email,
            courses = courses,
            requestedCourses = requestedCourses
        )
    }

    fun getCourseFromFirestoreDocument(snapshot: DocumentSnapshot): Course {

        val courseCreator: String =
            if (snapshot[COURSE_CREATOR] != null) snapshot[COURSE_CREATOR] as String else ""
        val courseDepartment: String =
            if (snapshot[COURSE_DEPARTMENT] != null) snapshot[COURSE_DEPARTMENT] as String else ""
        val courseSemester: String =
            if (snapshot[COURSE_SEMESTER] != null) snapshot[COURSE_SEMESTER] as String else ""
        val courseCode: String =
            if (snapshot[COURSE_CODE] != null) snapshot[COURSE_CODE] as String else ""
        val courseTitle: String =
            if (snapshot[COURSE_TITLE] != null) snapshot[COURSE_TITLE] as String else ""
        val courseCredit: Float =
            if (snapshot[COURSE_CREDIT] != null) snapshot[COURSE_CREATOR] as Float else 0f
        val courseTeacher: String =
            if (snapshot[COURSE_TEACHER] != null) snapshot[COURSE_TEACHER] as String else ""

//        val course = Course(
//            courseCreator = courseCreator,
//            courseDepartment = courseDepartment,
//            courseSemester = courseSemester,
//            courseCode = courseCode,
//            courseTitle = courseTitle,
//            courseCredit = courseCredit,
//            courseTeacher = courseTeacher
//        )
//
//        Log.d(TAG, "getCourseFromFirestoreDocument: $course")

        return Course(
            courseCreator = courseCreator,
            courseDepartment = courseDepartment,
            courseSemester = courseSemester,
            courseCode = courseCode,
            courseTitle = courseTitle,
            courseCredit = courseCredit,
            courseTeacher = courseTeacher
        )
    }


    fun getClassDetailsFromFirestoreDocument(snapshot: DocumentSnapshot): ClassDetails {

        val weekDay: String = if (snapshot[WEEKDAY] != null) snapshot[WEEKDAY] as String else ""
        val classroom: String =
            if (snapshot[CLASSROOM] != null) snapshot[CLASSROOM] as String else ""
        val section: String = if (snapshot[SECTION] != null) snapshot[SECTION] as String else ""
        val startHour: Int = if (snapshot[START_HOUR] != null) snapshot[START_HOUR] as Int else -1
        val startMinute: Int =
            if (snapshot[START_MINUTE] != null) snapshot[START_MINUTE] as Int else -1
        val startShift: String =
            if (snapshot[START_SHIFT] != null) snapshot[START_SHIFT] as String else ""
        val endHour: Int = if (snapshot[END_HOUR] != null) snapshot[END_HOUR] as Int else -1
        val endMinute: Int = if (snapshot[END_MINUTE] != null) snapshot[END_MINUTE] as Int else -1
        val endShift: String =
            if (snapshot[END_SHIFT] != null) snapshot[END_SHIFT] as String else ""

        return ClassDetails(
            weekDay = weekDay,
            classroom = classroom,
            section = section,
            startHour = startHour,
            startMinute = startMinute,
            startShift = startShift,
            endHour = endHour,
            endMinute = endMinute,
            endShift = endShift
        )
    }

    private const val TAG = "GetModelFromDocument"
}