package io.amuse.codeassignment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import io.amuse.codeassignment.domain.model.CatModel
import io.amuse.codeassignment.viewmodel.CatsViewModel

@Composable
fun CatsScreen(
    viewModel: CatsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is DataState.Error -> {
        }
        DataState.Loading -> {
        }
        is DataState.Success -> {
            val catsList = (state as DataState.Success<List<CatModel>>).data
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(catsList) { index, item ->
                    val color = Color.Black
                    CatItem(modifier = Modifier.background(color), catModel = item)
                }
            }
        }
    }
}
