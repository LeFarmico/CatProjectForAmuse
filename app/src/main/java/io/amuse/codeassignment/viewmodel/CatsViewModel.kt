package io.amuse.codeassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.amuse.codeassignment.domain.model.Cat
import io.amuse.codeassignment.repository.CatsRepository
import io.amuse.codeassignment.ui.DataUIStateWrapper
import io.amuse.codeassignment.ui.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed

class CatsViewModel(private val catsRepository: CatsRepository = CatsRepository()) : ViewModel() {
     fun getCats(): StateFlow<DataUIStateWrapper<List<Cat>>> = channelFlow {
        trySend(DataUIStateWrapper(emptyList<Cat>(), UIState.Loading))
        catsRepository.fetchCats().cancellable()
            .catch {
                trySend(DataUIStateWrapper(emptyList<Cat>(), UIState.Failure))
            }.collectLatest {
                trySend(DataUIStateWrapper(it, UIState.Success))
            }
    }.flowOn(Dispatchers.IO).stateIn(
        viewModelScope,
        WhileSubscribed(500),
        DataUIStateWrapper(emptyList(), UIState.Loading)
    )
}
