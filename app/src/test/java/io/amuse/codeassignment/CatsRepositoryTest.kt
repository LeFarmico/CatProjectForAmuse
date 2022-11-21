package io.amuse.codeassignment

import io.amuse.codeassignment.domain.api.CatsApi
import io.amuse.codeassignment.domain.model.CatCountModel
import io.amuse.codeassignment.domain.model.NetworkResponse
import io.amuse.codeassignment.repository.impl.CatsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class CatsRepositoryTest {

    private val catsCountModel = CatCountModel(1114)

    private val repoResponse = (0..19).map {
        MockData.catViewDataModel()
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchCats() {

        val api: CatsApi = mock {
            onBlocking { getJpegCat() } doReturn NetworkResponse.Success(MockData.catModel())
            onBlocking { getCatsCount() } doReturn NetworkResponse.Success(catsCountModel)
        }
        val repositoryImpl = CatsRepositoryImpl(api)

        runTest {
            repositoryImpl.fetchCats(
                onStart = {},
                onError = {}
            ).collect {
                assertEquals(repoResponse, it)
            }
        }
    }
    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchCatsCount() {

        val api: CatsApi = mock {
            onBlocking { getJpegCat() } doReturn NetworkResponse.Success(MockData.catModel())
            onBlocking { getCatsCount() } doReturn NetworkResponse.Success(catsCountModel)
        }
        val repositoryImpl = CatsRepositoryImpl(api)

        runTest {
            repositoryImpl.fetchCatsCount(
                onError = {}
            ).collect {
                assertEquals(catsCountModel.count, it)
            }
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchCatsWithException() {

        val exceptionText = "Ex"
        val exception = RuntimeException(exceptionText)
        val api: CatsApi = mock {
            onBlocking { getJpegCat() } doReturn NetworkResponse.Exception(exception)
            onBlocking { getCatsCount() } doReturn NetworkResponse.Exception(exception)
        }
        val repositoryImpl = CatsRepositoryImpl(api)

        runTest {
            repositoryImpl.fetchCats(
                onStart = {},
                onError = {
                    assertEquals(exceptionText, it)
                }
            ).collect()
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchCatsCountWithException() {

        val exceptionText = "Ex"
        val exception = RuntimeException(exceptionText)
        val api: CatsApi = mock {
            onBlocking { getJpegCat() } doReturn NetworkResponse.Exception(exception)
            onBlocking { getCatsCount() } doReturn NetworkResponse.Exception(exception)
        }
        val repositoryImpl = CatsRepositoryImpl(api)

        runTest {
            repositoryImpl.fetchCatsCount(
                onError = {
                    assertEquals(exceptionText, it)
                }
            ).collect()
        }
    }
}
