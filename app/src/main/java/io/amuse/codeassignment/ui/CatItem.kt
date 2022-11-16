package io.amuse.codeassignment.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.amuse.codeassignment.domain.model.Cat

@Composable
fun CatItem(modifier: Modifier, cat: Cat) {
    Text(modifier = modifier, text = "Url to cat: ${cat.url}", color = Color.White)
}