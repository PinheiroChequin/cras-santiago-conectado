package com.projeto.cras.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class UserViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _updateStatus = MutableLiveData<RequestStatus>()
    val updateStatus: LiveData<RequestStatus> = _updateStatus

    fun loadUser(userId: String) {
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val userData = document.toObject(User::class.java)
                _user.value = userData
            }
            .addOnFailureListener {
                _user.value = null
            }
    }

    fun updateUser(userId: String, updatedUser: User) {
        db.collection("users").document(userId).set(updatedUser)
            .addOnSuccessListener {
                _updateStatus.value = RequestStatus.Success
            }
            .addOnFailureListener {
                _updateStatus.value = RequestStatus.Failure(it.message ?: "Erro ao atualizar")
            }
    }

    fun updateSensitiveData(userId: String, newCpf: String, newPassword: String) {
        // Aqui, você pode adicionar lógica para verificar a senha atual
        // Por exemplo, verificando no banco de dados se a senha atual está correta

        val updates = hashMapOf<String, Any>(
            "cpf" to newCpf,
            "password" to newPassword // Lembre-se de que você deve tratar a senha de maneira segura, como criptografá-la
        )

        db.collection("users").document(userId).update(updates)
            .addOnSuccessListener {
                _updateStatus.value = RequestStatus.Success
            }
            .addOnFailureListener {
                _updateStatus.value = RequestStatus.Failure(it.message ?: "Erro ao atualizar")
            }
    }
}


data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val cpf: String = "",
    val password: String = ""
)
