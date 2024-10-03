package com.projeto.cras.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.DialogProperties

@Composable
fun ConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit, password: String, confirmPassword: String) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Confirmação de Atualização") },
        text = { Text(text = "Tem certeza que deseja atualizar CPF e senha?") },
        confirmButton = {
            Button(onClick = {
                onConfirm()
            }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    )
}
