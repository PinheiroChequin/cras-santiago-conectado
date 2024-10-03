package com.projeto.cras

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projeto.cras.ui.activities.HomeActivity
import com.projeto.cras.ui.activities.LoginActivity
import com.projeto.cras.ui.activities.ResourcesActivity
import com.projeto.cras.ui.activities.SignUpActivity
import com.projeto.cras.ui.activities.UserEditActivity
import com.projeto.cras.viewmodel.AuthViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier,authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login"){
            LoginActivity(modifier, navController, authViewModel)
        }
        composable("signup"){
            SignUpActivity(modifier, navController, authViewModel)
        }
        composable("home"){
            HomeActivity(modifier, navController, authViewModel)
        }
        composable("resources") {
            ResourcesActivity(modifier, navController)
        }
        composable("user_edit/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            if (userId != null) {
                UserEditActivity(userId = userId, navController = navController)
            }
        }
    })
}
