package io.amuse.codeassignment.domain.model

import kotlinx.serialization.Serializable

// If needed, can be changed to CatModelDto to encapsulate logic from domain layer and repository layer
@Serializable
data class CatModel(
    val url: String = "",
    val createdAt: String = ""
)
