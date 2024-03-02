package com.nasiat_muhib.classmate.core

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.strings.BLOOD_GROUP
import com.nasiat_muhib.classmate.strings.CREATED_COURSES
import com.nasiat_muhib.classmate.strings.DEPARTMENT
import com.nasiat_muhib.classmate.strings.EMAIL
import com.nasiat_muhib.classmate.strings.ENROLLED_COURSES
import com.nasiat_muhib.classmate.strings.FIRST_NAME
import com.nasiat_muhib.classmate.strings.LAST_NAME
import com.nasiat_muhib.classmate.strings.PHONE_NO
import com.nasiat_muhib.classmate.strings.REQUESTED_COURSES
import com.nasiat_muhib.classmate.strings.ROLE
import com.nasiat_muhib.classmate.strings.SESSION

object GetModelFromDocument {
    fun getUserFromFirestoreDocument(snapshot: DocumentSnapshot): User {

        val requestedCourses =
            if (snapshot[REQUESTED_COURSES] is List<*> && snapshot[REQUESTED_COURSES] != null)
                snapshot[REQUESTED_COURSES] as List<String>
            else emptyList()

        val createdCourses =
            if (snapshot[CREATED_COURSES] is List<*> && snapshot[CREATED_COURSES] != null)
                snapshot[CREATED_COURSES] as List<String>
            else emptyList()

        val enrolledCourses =
            if (snapshot[ENROLLED_COURSES] is List<*> && snapshot[ENROLLED_COURSES] != null)
                snapshot[ENROLLED_COURSES] as List<String>
            else emptyList()

        val firstName = if(snapshot[FIRST_NAME] != null ) snapshot[FIRST_NAME] as String else ""
        val lastName = if(snapshot[LAST_NAME] != null ) snapshot[LAST_NAME] as String else ""
        val role = if(snapshot[ROLE] != null ) snapshot[ROLE] as String else ""
        val department = if(snapshot[DEPARTMENT] != null ) snapshot[DEPARTMENT] as String else ""
        val session = if(snapshot[SESSION] != null ) snapshot[SESSION] as String else ""
        val bloodGroup = if(snapshot[BLOOD_GROUP] != null ) snapshot[BLOOD_GROUP] as String else ""
        val phoneNo = if(snapshot[PHONE_NO] != null ) snapshot[PHONE_NO] as String else ""
        val email = if(snapshot[EMAIL] != null ) snapshot[EMAIL] as String else ""
        
        val user = User(firstName, lastName, role, department, session, bloodGroup, phoneNo, email, createdCourses, enrolledCourses, requestedCourses)
        Log.d(TAG, "getUserFromFirestoreDocument: $user")

        return User(
            firstName = firstName,
            lastName = lastName,
            role = role,
            department = department,
            session = session,
            bloodGroup = bloodGroup,
            phoneNo = phoneNo,
            email = email,
            createdCourses = createdCourses,
            enrolledCourses = enrolledCourses,
            requestedCourses = requestedCourses
        )
    }
    
    private const val TAG = "GetModelFromDocument"
}