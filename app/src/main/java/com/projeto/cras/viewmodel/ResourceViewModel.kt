package com.projeto.cras.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class ResourceViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _resources = MutableLiveData<List<Resource>>()
    val resources: LiveData<List<Resource>> = _resources

    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus> = _requestStatus

    init {
        loadResources()
    }

    private fun loadResources() {
        Log.d("ResourceViewModel", "Iniciando carregamento dos recursos")
        db.collection("resources")
            .get()
            .addOnSuccessListener { result ->
                val resourceList = result.documents.map { document ->
                    Resource(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        description = document.getString("description") ?: "",
                        amount = document.getLong("amount")?.toInt() ?: 0
                    )
                }
                _resources.value = resourceList
                Log.d("ResourceViewModel", "Recursos carregados com sucesso: ${resourceList.size} recursos")
            }
            .addOnFailureListener { exception ->
                _resources.value = emptyList()
                Log.e("ResourceViewModel", "Falha ao carregar os recursos: ${exception.message}")
            }
    }

    // Função para solicitar recurso
    fun requestResource(resourceId: String, userId: String, justification: String) {
        val request = hashMapOf(
            "resourceId" to resourceId,
            "userId" to userId,
            "justification" to justification
        )

        Log.d("ResourceViewModel", "Solicitando recurso: $resourceId por usuário: $userId")
        db.collection("resourceRequests")
            .add(request)
            .addOnSuccessListener {
                _requestStatus.value = RequestStatus.Success
                Log.d("ResourceViewModel", "Recurso solicitado com sucesso")
            }
            .addOnFailureListener { exception ->
                _requestStatus.value = RequestStatus.Failure(exception.message ?: "Error")
                Log.e("ResourceViewModel", "Falha ao solicitar o recurso: ${exception.message}")
            }
    }
}

data class Resource(
    val id: String,
    val name: String,
    val description: String,
    val amount: Int
)

sealed class RequestStatus {
    data object Success : RequestStatus()
    data class Failure(val message: String) : RequestStatus()
}
