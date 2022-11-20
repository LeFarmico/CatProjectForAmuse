package io.amuse.codeassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val catsList: List<CatViewDataModel> = emptyList(),
    val catsCount: Int? = null,
    val loadedCatsCount: Int? = null
)

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val catsRepository: CatsRepository
) : ViewModel() {

    init {
        getCats()
    }

    private val _state: MutableStateFlow<DataState<CatScreenState>> = MutableStateFlow(DataState.Loading)
    val state = _state.stateIn(
        viewModelScope,
        WhileSubscribed(500),
        DataState.Loading
    )

    fun getCats() {
        viewModelScope.launch {
            catsRepository.fetchCats(
                onStart = { _state.value = DataState.Loading },
                onError = { _state.value = DataState.Error(it) }
            )
                .flowOn(Dispatchers.IO)
                .collectLatest {
                    // sequential request after collecting cats
                    val catsCount = getCatsCount()

                    _state.value = DataState.Success(
                        getCatScreenState().copy(
                            catsList = it,
                            catsCount = catsCount,
                            loadedCatsCount = it.size
                        )
                    )
                }
        }
    }

    // can't make concurrent request because of crash application (unexpected NPE)
    private suspend fun getCatsCount(): Int {
        return catsRepository.fetchCatsCount()
            .flowOn(Dispatchers.IO)
            .first()
    }

    // getting current success state if exist, if not creating a new one
    private fun getCatScreenState(): CatScreenState = when (_state.value) {
        is DataState.Success -> (_state.value as DataState.Success<CatScreenState>).data
        else -> CatScreenState()
    }
}
