package io.amuse.codeassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.amuse.codeassignment.domain.model.CatModel
import io.amuse.codeassignment.repository.api.CatsRepository
import io.amuse.codeassignment.ui.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel @Inject constructor(
    private val catsRepository: CatsRepository
) : ViewModel() {

    init {
        getCats()
    }

    private val _state: MutableStateFlow<DataState<List<CatModel>>> = MutableStateFlow(DataState.Loading)
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
                    _state.value = DataState.Success(it)
                }
        }
    }
}
