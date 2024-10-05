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
import com.projeto.cras.models.Resource
import com.projeto.cras.viewmodel.AuthViewModel
import com.projeto.cras.viewmodel.RequestStatus
import com.projeto.cras.viewmodel.ResourceViewModel

@Composable
fun ConsultResourcesActivity(
    modifier: Modifier = Modifier,
    navController: NavController,
    resourceViewModel: ResourceViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val resources by resourceViewModel.resources.observeAsState(emptyList())
    val filteredResources = resources.filter { !it.isRequestable }
    val requestStatus by resourceViewModel.requestStatus.observeAsState()

    var selectedResource by remember { mutableStateOf<Resource?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Recursos Disponíveis", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        filteredResources.forEach { resource ->
            ResourceItem(resource = resource) {
                selectedResource = resource
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}