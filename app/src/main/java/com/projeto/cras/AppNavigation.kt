package com.projeto.cras

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projeto.cras.Activities.HomeActivity
import com.projeto.cras.Activities.LoginActivity
import com.projeto.cras.Activities.ResourcesActivity
import com.projeto.cras.Activities.SignUpActivity


@Composable
fun AppNavigation(modifier: Modifier = Modifier,authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login"){
            LoginActivity(modifier,navController,authViewModel)
        }
        composable("signup"){
            SignUpActivity(modifier,navController,authViewModel)
        }
        composable("home"){
            HomeActivity(modifier,navController,authViewModel)
        }
        composable("resources"){
            ResourcesActivity(modifier,navController)
        }
    })
}
