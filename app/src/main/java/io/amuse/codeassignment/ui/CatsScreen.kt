package io.amuse.codeassignment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import io.amuse.codeassignment.viewmodel.CatsViewModel

@Composable
fun CatsScreen(
    viewModel: CatsViewModel = hiltViewModel()
) {
    val catsState by viewModel.getCats().collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        itemsIndexed(catsState.input ?: emptyList()) { index, item ->
            val color = Color.Black
            CatItem(modifier = Modifier.background(color), cat = item)
        }
    }
}
