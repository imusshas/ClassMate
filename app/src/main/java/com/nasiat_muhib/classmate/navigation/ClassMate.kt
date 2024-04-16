package com.nasiat_muhib.classmate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.nasiat_muhib.classmate.core.Constants.NOTIFICATION_URI
import com.nasiat_muhib.classmate.presentation.auth.forgot_password.ForgotPasswordScreen
import com.nasiat_muhib.classmate.presentation.auth.sign_in.SignInScreen
import com.nasiat_muhib.classmate.presentation.auth.sign_up.SignUpScreen
import com.nasiat_muhib.classmate.presentation.main.components.display_course.CourseDetailsDisplay
import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterScreen
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.CreateCourseViewModel
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.create.CreateCourse
import com.nasiat_muhib.classmate.presentation.main.create_semester.components.create.SearchTeacherScreen
import com.nasiat_muhib.classmate.presentation.main.enroll_course.EnrollCourseScreen
import com.nasiat_muhib.classmate.presentation.main.home.HomeScreen
import com.nasiat_muhib.classmate.presentation.main.menu.MenuScreen
import com.nasiat_muhib.classmate.presentation.main.menu.profile.ProfileScreen
import com.nasiat_muhib.classmate.presentation.main.menu.routine.RoutineScreen
import com.nasiat_muhib.classmate.presentation.main.notification.NotificationScreen

@Composable
fun ClassMate(
    navigationViewModel: NavigationViewModel = viewModel(),
) {

    val createCourseViewModel: CreateCourseViewModel = hiltViewModel()

    val course by navigationViewModel.course.collectAsState()

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (Firebase.auth.currentUser != null) {
            Screen.HomeScreen.route
        } else {
            Screen.SignInScreen.route
        }
    ) {
        /***************************  Authentication  *******************************/
        composable(Screen.SignInScreen.route) {
            SignInScreen(
                navigateToHomeScreen = {
                    navigateTo(navController, Screen.HomeScreen)
                },
                navigateToForgotPasswordScreen = {
                    navigateWithoutClearingBackStack(navController, Screen.ForgotPasswordScreen)
                },
                navigateToSignUpScreen = {
                    navigateWithoutClearingBackStack(navController, Screen.SignUpScreen)
                }
            )
        }
        composable(Screen.SignUpScreen.route) {
            SignUpScreen(
                navigateToHomeScreen = { navigateTo(navController, Screen.HomeScreen) },
                navigateBackToSignInScreen = { navigateBack(navController) }
            )
        }
        composable(Screen.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(
                navigateToSignInScreen = {
                    navigateTo(navController, Screen.SignInScreen)
                }
            )
        }
        /***************************  Authentication  *******************************/

        /***************************  Tab Item  *******************************/
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                navigationViewModel = navigationViewModel,
                navigateToTab = {tabItem ->
                    navigateToTab(navController, tabItem)
                },
                navigateToCourseDetailsDisplay = {
                    navigateWithoutClearingBackStack(navController, Screen.CourseDetailsDisplay)
                },
                recomposeHomeScreen = {
                    navigateTo(navController, Screen.HomeScreen)
                }
            )
        }
        composable(Screen.CreateSemesterScreen.route) {
            CreateSemesterScreen(
                navigationViewModel = navigationViewModel,
                navigateToTab = {tabItem ->
                    navigateToTab(navController, tabItem)
                },
                navigateToCreateCourse =  {
                    navigateTo(navController, Screen.CreateCourse)
                },
                navigateToCourseDetailsDisplay = {
                    navigateWithoutClearingBackStack(navController, Screen.CourseDetailsDisplay)
                }
            )
        }
        composable(Screen.EnrollCourseScreen.route) {
            EnrollCourseScreen(
                navigationViewModel = navigationViewModel,
                navigateToTab = {tabItem ->
                    navigateToTab(navController, tabItem)
                },
                navigateToCourseDetailsDisplay = {
                    navigateWithoutClearingBackStack(navController, Screen.CourseDetailsDisplay)
                }
            )
        }
        composable(
           route = Screen.NotificationScreen.route,
            deepLinks = listOf(navDeepLink { uriPattern = NOTIFICATION_URI })
        ) {
            NotificationScreen(
                navigateToTab = {tabItem ->
                    navigateToTab(navController, tabItem)
                },
                navigateToHomeScreen = {
                                       navigateTo(navController, Screen.HomeScreen)
                },
                navigateToCourseDetailsScreen = {
                                                navigateWithoutClearingBackStack(navController, Screen.CourseDetailsDisplay)
                },
                navigationViewModel = navigationViewModel,
            )
        }
        composable(Screen.MenuScreen.route) {
            MenuScreen(
                navigateToTab = {tabItem ->
                    navigateToTab(navController, tabItem)
                },
                navigateToMenu = { menuItem ->
                    navigateToMenu(navController, menuItem)
                }
            )
        }
        /***************************  Tab Item  *******************************/


        /***************************  Menu Item  *******************************/
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(
                navigateBackToMenu = { navigateBack(navController) }
            )
        }
        composable(Screen.RoutineScreen.route) {
            RoutineScreen(
                navigateBackToMenu = { navigateBack(navController) }
            )
        }
        /***************************  Menu Item  *******************************/


        /***************************  Create Course  *******************************/
        composable(Screen.CreateCourse.route) {
            CreateCourse(
                createCourseViewModel = createCourseViewModel,
                navigateToSearchTeacher = { navigateWithoutClearingBackStack(navController, Screen.SearchTeacher) },
                navigateBackToCreateSemester = { navigateTo(navController, Screen.CreateSemesterScreen) }
            )
        }
        composable(Screen.SearchTeacher.route) {
            SearchTeacherScreen(
                createCourseViewModel = createCourseViewModel,
                navigateToCreateCourse = { navigateTo(navController, Screen.CreateCourse) }
            )
        }
        /***************************  Create Course  *******************************/


        /***************************  Display Course  *******************************/
        composable(
            route = Screen.CourseDetailsDisplay.route,
        ) {
            CourseDetailsDisplay(course = course, navigateBack = { navigateBack(navController) }, recomposeCourseDetailsDisplay = { navigateWithoutClearingBackStack(navController, Screen.CourseDetailsDisplay) })
        }
        /***************************  Display Course  *******************************/
    }
}

private fun navigateTo(navController: NavController, destination:Screen) {
    navController.navigate(destination.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
        launchSingleTop = true
    }
}

private fun navigateWithoutClearingBackStack(navController: NavController, destination: Screen) {
    navController.navigate(destination.route) {
        launchSingleTop = true
    }
}

private fun navigateBack(navController: NavController) {
    if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        navController.popBackStack()
    }
}

private fun navigateToTab(navController: NavController, tabItem: TabItem) {
    when (tabItem) {
        TabItem.Home -> navigateTo(navController, Screen.HomeScreen)
        TabItem.CreateSemester -> navigateTo(navController, Screen.CreateSemesterScreen)
        TabItem.EnrollCourse -> navigateTo(navController, Screen.EnrollCourseScreen)
        TabItem.Notification -> navigateTo(navController, Screen.NotificationScreen)
        TabItem.Menu -> navigateTo(navController, Screen.MenuScreen)
    }
}

private fun navigateToMenu(navController: NavController, menuItem: MenuItem) {
    when (menuItem) {
        MenuItem.Profile ->  navigateWithoutClearingBackStack(navController, Screen.ProfileScreen)
        MenuItem.Routine -> navigateWithoutClearingBackStack(navController, Screen.RoutineScreen)
        MenuItem.SignOut -> navigateTo(navController, Screen.SignInScreen)
    }
}