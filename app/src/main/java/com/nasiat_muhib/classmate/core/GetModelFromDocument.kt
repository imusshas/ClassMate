package com.nasiat_muhib.classmate.core

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.Event
import com.nasiat_muhib.classmate.data.model.Post
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.strings.ACTIVE_STATUS
import com.nasiat_muhib.classmate.strings.BLOOD_GROUP
import com.nasiat_muhib.classmate.strings.CLASSROOM
import com.nasiat_muhib.classmate.strings.CLASS_COURSE_CODE
import com.nasiat_muhib.classmate.strings.CLASS_DEPARTMENT
import com.nasiat_muhib.classmate.strings.CLASS_NO
import com.nasiat_muhib.classmate.strings.COURSES
import com.nasiat_muhib.classmate.strings.COURSE_CLASSES
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
import com.nasiat_muhib.classmate.strings.EVENT_CLASSROOM
import com.nasiat_muhib.classmate.strings.EVENT_COURSE_CODE
import com.nasiat_muhib.classmate.strings.EVENT_DAY
import com.nasiat_muhib.classmate.strings.EVENT_DEPARTMENT
import com.nasiat_muhib.classmate.strings.EVENT_HOUR
import com.nasiat_muhib.classmate.strings.EVENT_MINUTE
import com.nasiat_muhib.classmate.strings.EVENT_MONTH
import com.nasiat_muhib.classmate.strings.EVENT_NO
import com.nasiat_muhib.classmate.strings.EVENT_SHIFT
import com.nasiat_muhib.classmate.strings.EVENT_TYPE
import com.nasiat_muhib.classmate.strings.EVENT_YEAR
import com.nasiat_muhib.classmate.strings.FIRST_NAME
import com.nasiat_muhib.classmate.strings.LAST_NAME
import com.nasiat_muhib.classmate.strings.PENDING_STATUS
import com.nasiat_muhib.classmate.strings.PHONE_NO
import com.nasiat_muhib.classmate.strings.POST_COURSE_CODE
import com.nasiat_muhib.classmate.strings.POST_CREATOR
import com.nasiat_muhib.classmate.strings.POST_CREATOR_FIRST_NAME
import com.nasiat_muhib.classmate.strings.POST_CREATOR_LAST_NAME
import com.nasiat_muhib.classmate.strings.POST_DESCRIPTION
import com.nasiat_muhib.classmate.strings.POST_TIMESTAMP
import com.nasiat_muhib.classmate.strings.REQUESTED_COURSES
import com.nasiat_muhib.classmate.strings.ROLE
import com.nasiat_muhib.classmate.strings.SECTION
import com.nasiat_muhib.classmate.strings.SESSION
import com.nasiat_muhib.classmate.strings.START_HOUR
import com.nasiat_muhib.classmate.strings.START_MINUTE
import com.nasiat_muhib.classmate.strings.START_SHIFT
import com.nasiat_muhib.classmate.strings.WEEKDAY
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

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
        val courses = if (snapshot[COURSES] != null && snapshot[COURSES] is List<*>) snapshot[COURSES] as List<String> else emptyList()
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
        val courseCredit: Double =
            if (snapshot[COURSE_CREDIT] != null) snapshot[COURSE_CREDIT] as Double else 0.0
        val courseTeacher: String =
            if (snapshot[COURSE_TEACHER] != null) snapshot[COURSE_TEACHER] as String else ""
        val pendingStatus: Boolean =
            if (snapshot[PENDING_STATUS] != null) snapshot[PENDING_STATUS] as Boolean else true
        val courseClasses: List<String> =
            if (snapshot[COURSE_CLASSES] != null && snapshot[COURSE_CLASSES] is List<*>) snapshot[COURSE_CLASSES] as List<String> else emptyList()

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
            courseClasses = courseClasses,
            courseTeacher = courseTeacher,
            pendingStatus = pendingStatus
        )
    }


    fun getClassDetailsFromFirestoreDocument(snapshot: DocumentSnapshot): ClassDetails {

        val classDepartment: String =
            if (snapshot[CLASS_DEPARTMENT] != null) snapshot[CLASS_DEPARTMENT] as String else ""
        val classCourseCode: String =
            if (snapshot[CLASS_COURSE_CODE] != null) snapshot[CLASS_COURSE_CODE] as String else ""
        val classNo: Long = if (snapshot[CLASS_NO] != null) snapshot[CLASS_NO] as Long else -1
        val weekDay: String = if (snapshot[WEEKDAY] != null) snapshot[WEEKDAY] as String else ""
        val classroom: String =
            if (snapshot[CLASSROOM] != null) snapshot[CLASSROOM] as String else ""
        val section: String = if (snapshot[SECTION] != null) snapshot[SECTION] as String else ""
        val startHour: Long = if (snapshot[START_HOUR] != null) snapshot[START_HOUR] as Long else -1
        val startMinute: Long =
            if (snapshot[START_MINUTE] != null) snapshot[START_MINUTE] as Long else -1
        val startShift: String =
            if (snapshot[START_SHIFT] != null) snapshot[START_SHIFT] as String else ""
        val endHour: Long = if (snapshot[END_HOUR] != null) snapshot[END_HOUR] as Long else -1
        val endMinute: Long = if (snapshot[END_MINUTE] != null) snapshot[END_MINUTE] as Long else -1
        val endShift: String =
            if (snapshot[END_SHIFT] != null) snapshot[END_SHIFT] as String else ""
        val isActive: Boolean =
            if (snapshot[ACTIVE_STATUS] != null) snapshot[ACTIVE_STATUS] as Boolean else true

        return ClassDetails(
            classDepartment = classDepartment,
            classCourseCode = classCourseCode,
            classNo = classNo.toInt(),
            weekDay = weekDay,
            classroom = classroom,
            section = section,
            startHour = startHour.toInt(),
            startMinute = startMinute.toInt(),
            startShift = startShift,
            endHour = endHour.toInt(),
            endMinute = endMinute.toInt(),
            endShift = endShift,
            isActive = isActive
        )
    }

    fun getEventFromFirestoreDocument(snapshot: DocumentSnapshot): Event {
        val type: String = snapshot[EVENT_TYPE].toString()
        val eventNo: Long = if (snapshot[EVENT_NO] != null) snapshot[EVENT_NO] as Long else  -1
        val courseCode: String = snapshot[EVENT_COURSE_CODE].toString()
        val department: String = snapshot[EVENT_DEPARTMENT].toString()
        val classroom: String = snapshot[EVENT_CLASSROOM].toString()
        val day: Long = if (snapshot[EVENT_DAY] != null) snapshot[EVENT_DAY] as Long else  -1
        val month: String = snapshot[EVENT_MONTH].toString()
        val year: Long = if (snapshot[EVENT_YEAR] != null) snapshot[EVENT_YEAR] as Long else -1
        val hour: Long = if (snapshot[EVENT_HOUR] != null) snapshot[EVENT_HOUR] as Long else -1
        val minute: Long = if (snapshot[EVENT_MINUTE] != null) snapshot[EVENT_MINUTE] as Long else -1
        val shift: String = snapshot[EVENT_SHIFT].toString()

        return Event(
            type = type,
            eventNo = eventNo.toInt(),
            courseCode = courseCode,
            department = department,
            classroom = classroom,
            day = day.toInt(),
            month = month,
            year = year.toInt(),
            hour = hour.toInt(),
            minute = minute.toInt(),
            shift = shift
        )
    }

    fun getPostFromFirestoreDocument(snapshot: DocumentSnapshot): Post {
        val creator = snapshot[POST_CREATOR].toString()
        val timestamp = if (snapshot[POST_TIMESTAMP] != null) snapshot[POST_TIMESTAMP] as Long else 0L
        val firstName = snapshot[POST_CREATOR_FIRST_NAME].toString()
        val lastName = snapshot[POST_CREATOR_LAST_NAME].toString()
        val courseCode = snapshot[POST_COURSE_CODE].toString()
        val description = snapshot[POST_DESCRIPTION].toString()

        return Post(
            creator = creator,
            timestamp = timestamp,
            firstName = firstName,
            lastName = lastName,
            courseCode = courseCode,
            description = description
        )
    }

    private const val TAG = "GetModelFromDocument"
}