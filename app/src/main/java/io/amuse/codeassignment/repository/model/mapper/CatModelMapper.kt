package io.amuse.codeassignment.repository.model.mapper

import io.amuse.codeassignment.domain.CatsURL
import io.amuse.codeassignment.domain.model.CatModel
import io.amuse.codeassignment.repository.model.CatViewDataModel

fun CatModel.toViewData(): CatViewDataModel = CatViewDataModel(
    url = CatsURL.URL + this.imageUrl,
    createdAt = createdAt.parseDateTime()
)

private fun String.parseDateTime(): String? = try {
    // ("yyyy-MM-dd['T'HH:mm]") => ("yyyy-MM-dd")
    this.removeRange(10, this.length)
} catch (e: IndexOutOfBoundsException) {
    null
}
