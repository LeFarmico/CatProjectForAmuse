package io.amuse.codeassignment.domain.api

import io.amuse.codeassignment.domain.model.Cat

interface CatsApi {

    suspend fun getCat(): Cat
}
