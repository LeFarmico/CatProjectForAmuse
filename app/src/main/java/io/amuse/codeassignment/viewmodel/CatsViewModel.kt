package io.amuse.codeassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.amuse.codeassignment.repository.api.CatsRepository
import io.amuse.codeassignment.repository.model.CatViewDataModel
import io.amuse.codeassignment.ui.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CatScreenState(
    val catsList: Flow<PagingData<CatViewDataModel>>? = null,
    val catsCount: Int? = null,
    val loadedCatsCount: Int? = null
)

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val catsRepository: CatsRepository
) : ViewModel() {

    init {
        getState()
    }

    fun getState() {
        viewModelScope.launch {
            // better to handle each exception
            try {
                // possible to make it concurrent, but now it is useless
                val catsCount = getCatsCount()
                val pagingCatsFlow = getPagingCatsFlow()

                _state.value = DataState.Success(
                    getCatScreenState().copy(
                        catsList = pagingCatsFlow,
                        catsCount = catsCount
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = DataState.Error(e.message)
            }
        }
    }

    private val _state: MutableStateFlow<DataState<CatScreenState>> = MutableStateFlow(DataState.Loading)
    val state = _state.stateIn(
        viewModelScope,
        WhileSubscribed(500),
        DataState.Loading
    )

    private suspend fun getPagingCatsFlow(): Flow<PagingData<CatViewDataModel>> {
        return catsRepository.fetchPagingCats(
            onError = { _state.value = DataState.Error(it) }
        ).cachedIn(viewModelScope)
    }

    private suspend fun getCatsCount(): Int {
        return catsRepository.fetchCatsCount(
            onError = { _state.value = DataState.Error(it) }
        ).flowOn(Dispatchers.IO)
            .first()
    }

    // getting current success state if exist, if not creating a new one
    private fun getCatScreenState(): CatScreenState = when (_state.value) {
        is DataState.Success -> (_state.value as DataState.Success<CatScreenState>).data
        else -> CatScreenState()
    }
}
