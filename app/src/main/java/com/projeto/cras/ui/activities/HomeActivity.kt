package com.projeto.cras.ui.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.projeto.cras.viewmodel.AuthState
import com.projeto.cras.viewmodel.AuthViewModel
import com.projeto.cras.R

@Composable
fun HomeActivity(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {

    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }

    val userId = authViewModel.getCurrentUserId()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.logo_cras),
            contentDescription = "Logo da Aplicação",
            modifier = Modifier.size(300.dp)
        )

        Text(text = "Página Inicial", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate("consultResources")
        }) {
            Text(text = "Consultar recursos")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            navController.navigate("RequestResources")
        }) {
            Text(text = "Requisitar recursos")
        }

        Spacer(modifier = Modifier.height(8.dp))

        userId?.let {
            Button(onClick = {
                navController.navigate("user_edit/$it")
            }) {
                Text(text = "Editar dados")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            authViewModel.signout()
        }) {
            Text(text = "Sair")
        }
    }
}
