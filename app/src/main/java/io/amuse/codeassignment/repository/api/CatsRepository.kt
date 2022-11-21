package io.amuse.codeassignment.repository.api

import androidx.paging.PagingData
import io.amuse.codeassignment.repository.model.CatViewDataModel
import kotlinx.coroutines.flow.Flow

interface CatsRepository {

    suspend fun fetchCats(
        onStart: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<List<CatViewDataModel>>

    suspend fun fetchCatsCount(
        onError: (String?) -> Unit
    ): Flow<Int>

    suspend fun fetchPagingCats(
        onError: (String?) -> Unit
    ): Flow<PagingData<CatViewDataModel>>
}
