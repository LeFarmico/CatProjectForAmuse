package io.amuse.codeassignment.repository.api

import io.amuse.codeassignment.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatsRepository {

    suspend fun fetchCats(
        onStart: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<Cat>>
}
