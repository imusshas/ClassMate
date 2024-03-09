package com.nasiat_muhib.classmate.navigation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nasiat_muhib.classmate.presentation.auth.forgot_password.ForgotPasswordScreen
import com.nasiat_muhib.classmate.presentation.auth.sign_in.SignInScreen
import com.nasiat_muhib.classmate.presentation.auth.sign_up.SignUpScreen
import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterScreen
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.create.CreateCourse
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.create.SearchTeacherScreen
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.edit.EditCourse
import com.nasiat_muhib.classmate.presentation.main.enroll_course.EnrollCourseScreen
import com.nasiat_muhib.classmate.presentation.main.home.HomeScreen

@Composable
fun ClassMateSecondVersion() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Crossfade(targetState = ClassMateAppRouter.currentScreen, label = "") {
            when(it.value) {
                /***************** Authentication *************/
                Screen.SignInScreen -> {
                    SignInScreen()
                }
                Screen.SignUpScreen -> {
                    SignUpScreen()
                }

                Screen.ForgotPasswordScreen -> {
                    ForgotPasswordScreen()
                }
                /***************** Authentication *************/

                /***************** Tab Items *************/
                Screen.HomeScreen -> {
                    HomeScreen()
                }

                Screen.CreateSemesterScreen -> {
                    CreateSemesterScreen()
                }

                Screen.EnrollCourseScreen -> {
                    EnrollCourseScreen()
                }
                Screen.MenuScreen -> TODO()
                Screen.NotificationScreen -> TODO()
                /***************** Tab Items *************/


                /***************** Create Course *************/
                Screen.CreateCourse -> {
                    CreateCourse()
                }

                Screen.SearchTeacher -> {
                    SearchTeacherScreen()
                }

                is Screen.EditCourse -> {
                    EditCourse(course = (it.value as Screen.EditCourse).course)
                }

                is Screen.EditSearchTeacher -> {

                }
            }
        }
    }
}