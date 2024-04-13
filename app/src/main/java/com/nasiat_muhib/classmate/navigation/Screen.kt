package com.nasiat_muhib.classmate.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.nasiat_muhib.classmate.data.model.Course

sealed class Screen(val route: String) {
    data object SignInScreen: Screen("sign_in")
    data object SignUpScreen: Screen("sign_up")
    data object ForgotPasswordScreen: Screen("forgot_password")

    data object HomeScreen: Screen("home")
    data object CreateSemesterScreen: Screen("create_semester")
    data object EnrollCourseScreen: Screen("enroll_course")
    data object NotificationScreen: Screen("notification")
    data object MenuScreen: Screen("menu")


    data object CreateCourse: Screen("create_course")
    data object SearchTeacher: Screen("search_teacher")
    data object CourseDetailsDisplay: Screen("course_details")

    data object ProfileScreen: Screen("profile")
    data object RoutineScreen: Screen("routine")

}