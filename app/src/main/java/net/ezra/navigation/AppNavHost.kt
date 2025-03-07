package net.ezra.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.ezra.ui.AddLawyer.AddLawyer
import net.ezra.ui.Lawyers.LawyerList
import net.ezra.ui.SplashScreen
import net.ezra.ui.about.AboutScreen
import net.ezra.ui.auth.LoginScreen
import net.ezra.ui.auth.SignupScreen
import net.ezra.ui.Document.DocumentScreen
import net.ezra.ui.home.HomeScreen
import net.ezra.ui.students.AddStudents
import net.ezra.ui.students.StudentList


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH


) {
    BackHandler {
        navController.popBackStack()

    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {


        composable(ROUTE_LOGIN) {
            LoginScreen(navController = navController){}
        }


        composable(ROUTE_SIGNUP) {
            SignupScreen(navController = navController){}
        }


        composable(ROUTE_HOME) {
            HomeScreen(navController)
        }


        composable(ROUTE_ABOUT) {
            AboutScreen(navController)
        }

        composable(ROUTE_DOCUMENT) {
            DocumentScreen(navController)
        }

        composable(ROUTE_VIEW_LAWYER) {
            LawyerList(navController = navController, viewModel = viewModel())
        }

        composable(ROUTE_ADD_STUDENTS) {
            AddStudents(navController)
        }

        composable(ROUTE_SPLASH) {
            SplashScreen(navController)
        }

        composable(ROUTE_LAWYER) {
            AddLawyer(navController)
        }


        composable(ROUTE_VIEW_STUDENTS) {
            StudentList(navController = navController, viewModel = viewModel())
        }
































    }
}





