package io.amuse.codeassignment.ui

sealed class DataState<out T> {

    data class Success<out T>(val data: T) : DataState<T>()

    data class Error(val message: String? = null) : DataState<Nothing>()

    object Loading : DataState<Nothing>()
}
