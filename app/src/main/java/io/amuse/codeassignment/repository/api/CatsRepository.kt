package io.amuse.codeassignment.repository.api

import io.amuse.codeassignment.domain.model.CatModel
import kotlinx.coroutines.flow.Flow

interface CatsRepository {

    suspend fun fetchCats(
        onStart: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<CatModel>>
}
