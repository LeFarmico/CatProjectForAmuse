package io.amuse.codeassignment.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.amuse.codeassignment.R
import io.amuse.codeassignment.repository.model.CatViewDataModel
import io.amuse.codeassignment.ui.theme.ImageCornerShape

@Composable
fun CatItem(modifier: Modifier, catModel: CatViewDataModel) {
    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp)
                .padding(4.dp)
                .clip(shape = ImageCornerShape),
            model = ImageRequest.Builder(LocalContext.current)
                .data(catModel.url)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.cat_image_cd),
            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.Crop
        )
        // Drawing date only if it exists
        if (catModel.createdAt != null) {
            Text(modifier = modifier, text = catModel.createdAt, color = Color.White)
        }
    }
}
