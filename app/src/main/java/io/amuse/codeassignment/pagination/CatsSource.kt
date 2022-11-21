package io.amuse.codeassignment.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.model.NetworkResponse
import io.amuse.codeassignment.repository.model.CatViewDataModel
import io.amuse.codeassignment.repository.model.mapper.toViewData
import kotlinx.coroutines.*
import java.io.IOException
import java.nio.channels.UnresolvedAddressException

class CatsSource(
    private val api: CatsApi
) : PagingSource<Int, CatViewDataModel>() {

    override fun getRefreshKey(state: PagingState<Int, CatViewDataModel>): Int? {
        // no need to specify a page because api loads random cats
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatViewDataModel> {
        return try {

            val nextPage = params.key ?: 1

            val context = Dispatchers.IO + Job()
            val scope = CoroutineScope(context)
            val cats = (0..50).map {
                scope.async {
                    when (val networkResponse = api.getJpegCat()) {
                        is NetworkResponse.Error -> throw (networkResponse.throwable) // handle API request errors
                        is NetworkResponse.Exception -> throw (networkResponse.throwable) // handle user errors
                        is NetworkResponse.Success -> {
                            networkResponse.data.toViewData()
                        } // map model to viewData
                    }
                }
            }.toTypedArray()

            try {
                // *cats unpacks Array to vararg
                val response = awaitAll(*cats)
                LoadResult.Page(
                    data = response,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (response.isEmpty()) null else nextPage + 1
                )
            } catch (e: Throwable) {
                LoadResult.Error(e)
            }
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: UnresolvedAddressException) {
            return LoadResult.Error(e)
        }
    }
}
