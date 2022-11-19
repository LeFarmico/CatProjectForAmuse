package io.amuse.codeassignment.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Cat(
    val url: String = "",
    val createdAt: String = ""
)
