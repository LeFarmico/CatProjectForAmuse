package io.amuse.codeassignment.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatCountModel(
    @SerialName("number") val count: Int = 0
)
