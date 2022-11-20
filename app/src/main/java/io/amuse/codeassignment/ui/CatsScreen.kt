package io.amuse.codeassignment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import io.amuse.codeassignment.R
import io.amuse.codeassignment.ui.theme.EvenRowColor
import io.amuse.codeassignment.ui.theme.OddRowColor
import io.amuse.codeassignment.utils.InternetStatus
import io.amuse.codeassignment.utils.internetState
import io.amuse.codeassignment.viewmodel.CatScreenState
import io.amuse.codeassignment.viewmodel.CatsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun CatsScreen(
    viewModel: CatsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // can be encapsulated to ViewModel
    val internetState by internetState()

    // initial internet availability
    var isInternetAvailable by remember {
        mutableStateOf(internetState != InternetStatus.Unavailable)
    }

    // observing internet state as Boolean
    isInternetAvailable = internetState != InternetStatus.Unavailable

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // catch noInternet state after the cats list was loaded
        if (!isInternetAvailable) {
            NoInternetState()
            return@Box
        }
        when (state) {
            is DataState.Error -> {
                ErrorState(
                    message = (state as DataState.Error).message,
                    isInternetAvailable = isInternetAvailable,
                    onClick = { viewModel.getCats() }
                )
            }
            DataState.Loading -> {
                CircularProgressIndicator()
            }
            is DataState.Success -> {
                val screenState = (state as DataState.Success<CatScreenState>).data
                CatsContent(screenState = screenState)
            }
        }
    }
}

@Composable
fun ErrorState(
    modifier: Modifier = Modifier,
    message: String?,
    isInternetAvailable: Boolean,
    onClick: () -> Unit
) {

    if (!isInternetAvailable) {
        NoInternetState()
        return
    }
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message ?: stringResource(R.string.default_error))
        Button(
            onClick = { onClick() }
        ) {
            Text(text = stringResource(R.string.retry_button))
        }
    }
}

@Composable
fun NoInternetState(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.no_internet_connection)
    )
}

@Composable
fun CatsContent(
    modifier: Modifier = Modifier,
    screenState: CatScreenState
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cats count
        if (screenState.catsCount != null) {
            Text(text = "Total cats count: " + screenState.catsCount.toString())
            Text(text = "Loaded cats: " + screenState.loadedCatsCount.toString())
        }

        // List of cats
        LazyColumn(
            modifier = Modifier
                .wrapContentWidth()
                .fillMaxHeight()
        ) {
            itemsIndexed(screenState.catsList) { index, item ->
                val color = if (index % 2 == 0) OddRowColor else EvenRowColor
                CatItem(modifier = Modifier.background(color), catModel = item)
            }
        }
    }
}
