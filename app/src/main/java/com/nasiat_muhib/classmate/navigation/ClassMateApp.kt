package com.nasiat_muhib.classmate.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavController
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.google.firebase.Firebase
//import com.google.firebase.auth.auth
//import com.nasiat_muhib.classmate.presentation.auth.sign_in.SignInScreen
//import com.nasiat_muhib.classmate.presentation.auth.sign_up.SignUpScreen
//import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterScreen
//import com.nasiat_muhib.classmate.presentation.main.enroll_course.EnrollCourseScreen
//import com.nasiat_muhib.classmate.presentation.main.home.HomeScreen
//import com.nasiat_muhib.classmate.presentation.main.menu.MenuScreen
//import com.nasiat_muhib.classmate.presentation.main.menu.profile.ProfileScreen
//import com.nasiat_muhib.classmate.presentation.main.notification.NotificationScreen
//
//@Composable
//fun ClassMateApp() {
//    val navController: NavHostController = rememberNavController()
//
//    NavHost(
//        navController = navController, startDestination = if (Firebase.auth.currentUser != null)
//            TabItem.Home.name else Routes.SignIn.name
//    ) {
//
//        /******************************** Authentication *************************************/
//
//        composable(route = Routes.SignIn.name) {
//            SignInScreen(
//                navigateToHomeScreen = { navigateTo(navController, TabItem.Home.name) },
//                navigateToForgotPasswordScreen = {
//                    navigateTo(
//                        navController,
//                        Routes.ForgotPassword.name
//                    )
//                },
//                navigateToSignUpScreen = { navigateTo(navController, Routes.SignUp.name) }
//            )
//        }
//
//        composable(route = Routes.SignUp.name) {
//            SignUpScreen(
//                navigateToHomeScreen = {
//                    navigateTo(navController, TabItem.Home.name)
//                },
//                navigateToSignInScreen = {
//                    navigateTo(navController, Routes.SignIn.name)
//                }
//            )
//        }
//
//
//        composable(route = Routes.ForgotPassword.name) {
//            /*TODO*/
//        }
//
//        /******************************** Authentication *************************************/
//
//
//        /******************************** Main Screen *************************************/
//
//        composable(route = TabItem.Home.name) {
//            HomeScreen(
//                navigateToTab = {tabItem ->
//                    navigateTo(navController, tabItem.name)
//                }
//            )
//        }
//
//        composable(route = TabItem.CreateSemester.name) {
//            CreateSemesterScreen(
//                navigateToTab = {tabItem ->
//                    navigateTo(navController, tabItem.name)
//                }
//            )
//        }
//
//        composable(route = TabItem.EnrollCourse.name) {
//            EnrollCourseScreen(
//                navigateToTab = {tabItem ->
//                    navigateTo(navController, tabItem.name)
//                }
//            )
//        }
//
//        composable(route = TabItem.Notification.name) {
//            NotificationScreen(
//                navigateToTab = {tabItem ->
//                    navigateTo(navController, tabItem.name)
//                }
//            )
//        }
//
//        composable(route = TabItem.Menu.name) {
//            MenuScreen(
//                navigateToTab = {tabItem ->
//                    navigateTo(navController, tabItem.name)
//                },
//                navigateToMenu = {menuItem ->
//                    navController.navigate(menuItem.name)
//                }
//            )
//        }
//
//        /******************************** Main Screen *************************************/
//
//
//        /******************************** MenuItem *************************************/
//
//        composable(route = MenuItem.Profile.name) {
//            ProfileScreen(navigateBackToMenuScreen = { navigateTo(navController, TabItem.Menu.name) })
//        }
//
//        composable(route = MenuItem.Routine.name) {
//            /*TODO*/
//        }
//
//        composable(route = MenuItem.SignOut.name) {
//            SignInScreen(
//                navigateToHomeScreen = { navigateTo(navController, TabItem.Home.name) },
//                navigateToForgotPasswordScreen = {
//                    navigateTo(
//                        navController,
//                        Routes.ForgotPassword.name
//                    )
//                },
//                navigateToSignUpScreen = { navigateTo(navController, Routes.SignUp.name) }
//            )
//        }
//
//        /******************************** MenuItem *************************************/
//
//
//        /******************************** Create Course  *************************************/
//
//
//        /******************************** Create Course *************************************/
//    }
//}
//
//private fun navigateTo(navController: NavController, route: String) {
//    navController.navigate(route) {
//        popUpTo(navController.graph.id) {
//            inclusive = true
//        }
//    }
//}