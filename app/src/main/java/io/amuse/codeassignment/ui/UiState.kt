package io.amuse.codeassignment.ui

enum class UIState {
    Loading,
    Success,
    Failure
}

data class DataUIStateWrapper<T>(val input: T?, val uiState: UIState)