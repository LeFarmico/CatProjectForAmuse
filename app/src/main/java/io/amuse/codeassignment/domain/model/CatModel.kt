package io.amuse.codeassignment.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// If needed, can be changed to CatModelDto to encapsulate logic from domain layer and repository layer
@Serializable
data class CatModel(
    // image url contains only the path of image (...com/$imageUrl)
    @SerialName("url") val imageUrl: String = "",
    // contains format of pattern ("yyyy-MM-dd['T'HH:mm]")
    @SerialName("createdAt") val createdAt: String = "",
    @SerialName("mimetype") val mimeType: String = ""
)
