package com.projeto.cras.models

data class Resource(
    val id: String,
    val name: String,
    val description: String,
    val isRequestable: Boolean,
    val amount: Int
)

