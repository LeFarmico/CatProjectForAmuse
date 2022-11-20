package io.amuse.codeassignment.repository.model

data class CatViewDataModel(
    // url contains the full path to the image
    val url: String,
    // contains parsed data format ("yyyy-MM-dd") can be changed by requirements
    val createdAt: String? = null
)
