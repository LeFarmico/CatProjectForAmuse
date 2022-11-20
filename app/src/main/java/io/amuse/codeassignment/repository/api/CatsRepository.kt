package io.amuse.codeassignment.repository.api

import io.amuse.codeassignment.repository.model.CatViewDataModel
import kotlinx.coroutines.flow.Flow

interface CatsRepository {

    suspend fun fetchCats(
        onStart: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<CatViewDataModel>>

    suspend fun fetchCatsCount(): Flow<Int>
}
