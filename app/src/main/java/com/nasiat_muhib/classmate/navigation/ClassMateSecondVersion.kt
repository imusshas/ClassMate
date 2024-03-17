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
import com.nasiat_muhib.classmate.presentation.main.components.display_course.CourseDetailsDisplay
import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterScreen
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.create.CreateCourse
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.create.SearchTeacherScreen
import com.nasiat_muhib.classmate.presentation.main.enroll_course.EnrollCourseScreen
import com.nasiat_muhib.classmate.presentation.main.home.HomeScreen
import com.nasiat_muhib.classmate.presentation.main.menu.MenuScreen
import com.nasiat_muhib.classmate.presentation.main.menu.profile.ProfileScreen
import com.nasiat_muhib.classmate.presentation.main.menu.routine.RoutineScreen
import com.nasiat_muhib.classmate.presentation.main.notification.NotificationScreen

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
                Screen.MenuScreen -> {
                    MenuScreen()
                }
                Screen.NotificationScreen -> {
                    NotificationScreen()
                }
                /***************** Tab Items *************/


                /***************** Create Course *************/
                Screen.CreateCourse -> {
                    CreateCourse()
                }

                Screen.SearchTeacher -> {
                    SearchTeacherScreen()
                }

                is Screen.CourseDetailsDisplay -> {
                    CourseDetailsDisplay(course = (it.value as Screen.CourseDetailsDisplay).course, screen = (it.value as Screen.CourseDetailsDisplay).screen )
                }

                Screen.ProfileScreen -> {
                    ProfileScreen()
                }

                Screen.RoutineScreen -> {
                    RoutineScreen()
                }
            }
        }
    }
}