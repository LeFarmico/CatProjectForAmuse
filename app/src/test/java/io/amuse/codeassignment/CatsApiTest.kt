package io.amuse.codeassignment

import io.amuse.codeassignment.domain.impl.CatsApiImpl
import io.amuse.codeassignment.domain.model.NetworkResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CatsApiTest {

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCatsCount() {
        val client = MockClient.client()

        val api = CatsApiImpl(client)

        runTest {
            val response = api.getCatsCount()
            assert(response is NetworkResponse.Success)
        }
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getJpegCats() {
        val client = MockClient.client()

        val api = CatsApiImpl(client)

        runTest {
            val response = api.getJpegCat()
            assert(response is NetworkResponse.Success)
        }
    }
}
