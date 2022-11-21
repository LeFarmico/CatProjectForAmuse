package io.amuse.codeassignment

import io.amuse.codeassignment.domain.model.CatCountModel
import io.amuse.codeassignment.domain.model.CatModel
import io.amuse.codeassignment.repository.model.CatViewDataModel

object MockData {

    fun catsCountModel() = CatCountModel(1114)

    fun catModel() = CatModel(
        createdAt = "2022-04-27T20:23:44.510Z",
        imageUrl = "/cat/nzb8sRlq6Zweozwx",
        mimeType = "image/jpeg"
    )

    fun failCatModel() = CatModel(
        createdAt = "01.12.2001",
        imageUrl = "nothing",
        mimeType = "image/png"
    )

    fun catModelList() = listOf(catModel())

    fun failCatModelList() = listOf(failCatModel())

    fun catViewDataModel() = CatViewDataModel(
        createdAt = "2022-04-27",
        url = "https://cataas.com/cat/nzb8sRlq6Zweozwx"
    )

    fun failCatViewDataModel() = CatViewDataModel(
        createdAt = "01.12.2001",
        url = "https://failcat.com"
    )

    fun catViewDataModelList() = listOf(catViewDataModel())

    fun failViewDataCatModelList() = listOf(failCatViewDataModel())
}