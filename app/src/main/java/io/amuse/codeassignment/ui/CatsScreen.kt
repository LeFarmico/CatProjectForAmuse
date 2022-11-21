package io.amuse.codeassignment.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
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
                    onClick = { viewModel.getState() }
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
fun LoadingState(
    modifier: Modifier = Modifier,
    loadingMessage: String? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            if (loadingMessage == null) return
            Text(text = loadingMessage)
        }
    }
}

@Composable
fun CatsContent(
    modifier: Modifier = Modifier,
    screenState: CatScreenState
) {

    val catsList = screenState.catsList?.collectAsLazyPagingItems()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cats count
        if (screenState.catsCount != null) {
            Text(text = "Total cats count: " + screenState.catsCount.toString())
            Text(text = "Loaded cats: " + screenState.loadedCatsCount.toString())
        }

        if (catsList == null) return

        // List of cats
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
        ) {
            itemsIndexed(catsList) { index, catItem ->
                val color = if (index % 2 == 0) OddRowColor else EvenRowColor
                CatItem(modifier = Modifier.background(color), catModel = catItem!!)
            }

            catsList.apply {
                when {
                    // next response page is loading
                    loadState.append is LoadState.Loading -> {
                        item {
                            LoadingState(
                                loadingMessage = "Next page Loading"
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        item { Text(text = "Error") }
                    }
                    // first time response page is loading
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            LoadingState(
                                loadingMessage = "Image Loading"
                            )
                        }
                    }
                    // first time response page is error
                    loadState.refresh is LoadState.Error -> {
                        item { Text(text = "Error") }
                    }
                }
            }
        }
    }
}
