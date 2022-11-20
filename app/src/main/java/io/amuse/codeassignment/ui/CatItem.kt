package io.amuse.codeassignment.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.amuse.codeassignment.domain.model.CatModel

@Composable
fun CatItem(modifier: Modifier, catModel: CatModel) {
    Text(modifier = modifier, text = "Url to cat: ${catModel.url}", color = Color.White)
}
