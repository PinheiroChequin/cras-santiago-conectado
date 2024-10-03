package com.projeto.cras.ui.activities

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.projeto.cras.ui.components.ConfirmationDialog
import com.projeto.cras.viewmodel.RequestStatus
import com.projeto.cras.viewmodel.User
import com.projeto.cras.viewmodel.UserViewModel

@Composable
fun UserEditActivity(
    userId: String,
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val user by userViewModel.user.observeAsState()
    val updateStatus by userViewModel.updateStatus.observeAsState()

    val context = LocalContext.current

    var name by remember { mutableStateOf(user?.name ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var cpf by remember { mutableStateOf(user?.cpf ?: "") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(user) {
        user?.let {
            name = it.name
            email = it.email
            cpf = it.cpf
        }
    }

    LaunchedEffect(updateStatus) {
        when (updateStatus) {
            is RequestStatus.Success -> {
                Toast.makeText(context, "Usuário atualizado com sucesso", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            is RequestStatus.Failure -> {
                Toast.makeText(
                    context, (updateStatus as RequestStatus.Failure).message, Toast.LENGTH_SHORT
                ).show()
            }
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Editar Usuário", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Nova Senha (opcional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirme a Senha") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val updatedUser = (password.ifBlank { null })?.let {
                    User(
                        id = userId,
                        name = name,
                        email = email,
                        cpf = cpf,
                        password = it
                    )
                }

                if (updatedUser != null) {
                    userViewModel.updateUser(userId, updatedUser)
                }

                if (password.isNotBlank() && password == confirmPassword) {
                    showConfirmationDialog = true
                } else if (password.isNotBlank()) {
                    Toast.makeText(context, "As senhas não coincidem!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Salvar")
        }

        if (showConfirmationDialog) {
            ConfirmationDialog(
                onConfirm = {
                    // userViewModel.updatePassword(userId, password)
                    showConfirmationDialog = false
                },
                onDismiss = { showConfirmationDialog = false },
                password = password,
                confirmPassword = confirmPassword
            )
        }
    }
}
