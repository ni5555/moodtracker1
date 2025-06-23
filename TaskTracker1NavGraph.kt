package com.example.tasktracker1.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tasktracker1.ui.theme.home.HomeScreen
import com.example.tasktracker1.ui.theme.list_detail.ListDetailScreen
import com.example.tasktracker1.ui.theme.task_detail.TaskDetailScreen
import com.example.tasktracker1.ui.theme.composables.VhodScreen
import com.example.tasktracker1.ui.theme.composables.RegistrationScreen

@Composable
fun TaskTracker1NavGraph(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(modifier = modifier, navController = navController)
        }
        composable(route = Screen.ListDetailScreen.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("id")
                ?.let { ListDetailScreen(navController = navController) }
        }
        composable(route = Screen.TaskDetailScreen.route) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("id")
                ?.let { TaskDetailScreen(modifier = modifier, navController = navController) }
        }
        composable("vhod") {
            VhodScreen(navController)
        }
        composable("Registration") {
            RegistrationScreen(navController)
        }

    }
}