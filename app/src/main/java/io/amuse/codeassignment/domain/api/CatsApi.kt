package io.amuse.codeassignment.domain.api

import io.amuse.codeassignment.domain.model.CatCountModel
import io.amuse.codeassignment.domain.model.CatModel
import io.amuse.codeassignment.domain.model.NetworkResponse

interface CatsApi {

    suspend fun getJpegCat(): NetworkResponse<CatModel>

    suspend fun getCatsCount(): NetworkResponse<CatCountModel>
}
