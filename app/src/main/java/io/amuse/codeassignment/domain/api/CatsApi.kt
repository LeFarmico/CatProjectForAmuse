package io.amuse.codeassignment.domain.api

import io.amuse.codeassignment.domain.model.Cat
import io.amuse.codeassignment.domain.model.NetworkResponse

interface CatsApi {

    suspend fun getCat(): NetworkResponse<Cat>
}
