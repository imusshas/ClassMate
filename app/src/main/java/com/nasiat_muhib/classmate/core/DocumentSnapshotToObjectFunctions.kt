package com.nasiat_muhib.classmate.core

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.nasiat_muhib.classmate.core.Constants.ACTIVE_STATUS
import com.nasiat_muhib.classmate.core.Constants.AVATAR_URL
import com.nasiat_muhib.classmate.core.Constants.FIRST_NAME
import com.nasiat_muhib.classmate.core.Constants.LAST_NAME
import com.nasiat_muhib.classmate.core.Constants.ROLE
import com.nasiat_muhib.classmate.core.Constants.DEPARTMENT
import com.nasiat_muhib.classmate.core.Constants.SESSION
import com.nasiat_muhib.classmate.core.Constants.BLOOD_GROUP
import com.nasiat_muhib.classmate.core.Constants.CODE
import com.nasiat_muhib.classmate.core.Constants.CREATED_COURSE
import com.nasiat_muhib.classmate.core.Constants.CREATOR
import com.nasiat_muhib.classmate.core.Constants.PHONE_NO
import com.nasiat_muhib.classmate.core.Constants.EMAIL
import com.nasiat_muhib.classmate.core.Constants.PASSWORD
import com.nasiat_muhib.classmate.core.Constants.ENROLLED_COURSE
import com.nasiat_muhib.classmate.core.Constants.ENROLLED_STUDENTS
import com.nasiat_muhib.classmate.core.Constants.HOUR
import com.nasiat_muhib.classmate.core.Constants.MINUTE
import com.nasiat_muhib.classmate.core.Constants.CLASSROOM
import com.nasiat_muhib.classmate.core.Constants.REQUESTED_COURSE
import com.nasiat_muhib.classmate.core.Constants.SHIFT
import com.nasiat_muhib.classmate.core.Constants.TAG
import com.nasiat_muhib.classmate.core.Constants.TEACHER
import com.nasiat_muhib.classmate.core.Constants.TITLE
import com.nasiat_muhib.classmate.core.Constants.WEEK_DAY
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User

object DocumentSnapshotToObjectFunctions {

    fun getUserFromDocumentSnapshot(userSnapshot: DocumentSnapshot): User {

        val firstName: String = userSnapshot[FIRST_NAME].toString()
        val lastName: String = userSnapshot[LAST_NAME].toString()
        val role: String = userSnapshot[ROLE].toString()
        val department: String = userSnapshot[DEPARTMENT].toString()
        val session: String = userSnapshot[SESSION].toString()
        val bloodGroup: String = userSnapshot[BLOOD_GROUP].toString()
        val phoneNo: String = userSnapshot[PHONE_NO].toString()
        val userEmail: String = userSnapshot[EMAIL].toString()
        val userPassword: String = userSnapshot[PASSWORD].toString()
        val avatarUrl: String = userSnapshot[AVATAR_URL].toString()
        val createdCourse: List<String> = if(userSnapshot[CREATED_COURSE] != null) userSnapshot[CREATED_COURSE]  as List<String> else emptyList()
        val enrolledCourses: List<String> = if(userSnapshot[ENROLLED_COURSE] != null) userSnapshot[ENROLLED_COURSE] as List<String> else emptyList()
        val requestedCourses: List<String> = if(userSnapshot[REQUESTED_COURSE] != null) userSnapshot[REQUESTED_COURSE] as List<String> else emptyList()

//                    Log.d(
//                        TAG,
//                        "variables: $firstName, $lastName, $role, $department, $bloodGroup, $phoneNo, $userEmail, $userPassword"
//                    )

        return User(
            firstName = firstName,
            lastName = lastName,
            role = role,
            department = department,
            session = session,
            bloodGroup = bloodGroup,
            phoneNo = phoneNo,
            email = userEmail,
            password = userPassword,
            avatarUrl = avatarUrl,
            createdCourse = createdCourse,
            enrolledCourse = enrolledCourses,
            requestedCourse = requestedCourses
        )
    }

    fun getCourseFromDocumentSnapshot(courseSnapshot: DocumentSnapshot): Course {

        val code: String = courseSnapshot[CODE].toString()
        val title: String = courseSnapshot[TITLE].toString()
        val creator: String = courseSnapshot[CREATOR].toString()
        val teacher: String = courseSnapshot[TEACHER].toString()
        val enrolledStudents: List<String> = if (courseSnapshot[ENROLLED_STUDENTS] != null) courseSnapshot[ENROLLED_STUDENTS] as List<String> else emptyList()

        return Course(
            code = code,
            title = title,
            creator = creator,
            teacher = teacher,
            enrolledStudents = enrolledStudents
        )
    }

    fun getClassDetailsFromDocumentSnapshot(classDetailsSnapshot: DocumentSnapshot): List<ClassDetails> {
        val classDetailsList: MutableList<ClassDetails> = mutableListOf()

        classDetailsSnapshot.data?.values?.forEach { details ->
            if (details is Map<*, *>) {
                val map: Map<String, Any> = details as Map<String, Any>
                val weekDay: String = map[WEEK_DAY] as String
                val hour: Int = map[HOUR] as Int
                val minute: Int = map[MINUTE] as Int
                val shift: String = map[SHIFT] as String
                val place: String = map[CLASSROOM] as String
                val isActive: Boolean = map[ACTIVE_STATUS] as Boolean

                val classDetails = ClassDetails(weekDay = weekDay, hour = hour, minute = minute, shift = shift, classroom = place, isActive = isActive)
                classDetailsList.add(classDetails)
            }
        }

        return classDetailsList
    }

    fun getMapFromClassDetailsList(classDetailsList: List<ClassDetails>): Map<String, Any> {
        val map: Map<String, Any> = classDetailsList.mapIndexed { index, classDetails ->
            index.toString() to classDetails.toMap()
        }.toMap()
        Log.d(TAG, "getMapFromClassDetails: $map")
        return map
    }
}