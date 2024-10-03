package com.projeto.cras.ui.activities

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.projeto.cras.viewmodel.AuthViewModel
import com.projeto.cras.viewmodel.RequestStatus
import com.projeto.cras.viewmodel.Resource
import com.projeto.cras.viewmodel.ResourceViewModel

@Composable
fun ResourcesActivity(
    modifier: Modifier = Modifier,
    navController: NavController,
    resourceViewModel: ResourceViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val resources by resourceViewModel.resources.observeAsState(emptyList())
    val requestStatus by resourceViewModel.requestStatus.observeAsState()

    val context = LocalContext.current
    var selectedResource by remember { mutableStateOf<Resource?>(null) }
    var justification by remember { mutableStateOf("") }

    val userId = authViewModel.getCurrentUserId()

    LaunchedEffect(requestStatus) {
        when (requestStatus) {
            is RequestStatus.Success -> {
                Toast.makeText(context, "Recurso solicitado com sucesso", Toast.LENGTH_SHORT).show()
                selectedResource = null
                justification = ""
            }
            is RequestStatus.Failure -> {
                Toast.makeText(
                    context, (requestStatus as RequestStatus.Failure).message, Toast.LENGTH_SHORT
                ).show()
            }
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Recursos Disponíveis", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        resources.forEach { resource ->
            ResourceItem(resource = resource) {
                selectedResource = resource
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedResource != null) {
            Text(text = "Solicitar: ${selectedResource!!.name}", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = justification,
                onValueChange = { justification = it },
                label = { Text("Justificativa") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (userId != null) {
                        resourceViewModel.requestResource(
                            resourceId = selectedResource!!.id,
                            userId = userId,
                            justification = justification
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Solicitar Recurso")
            }
        }
    }
}

@Composable
fun ResourceItem(resource: Resource, onSelect: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onSelect
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = resource.name, fontSize = 18.sp)
            Text(text = resource.description, fontSize = 14.sp)
        }
    }
}
