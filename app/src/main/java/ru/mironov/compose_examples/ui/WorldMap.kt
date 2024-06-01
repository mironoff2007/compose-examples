package ru.mironov.compose_examples.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.mironov.compose_examples.R

@Composable
fun WorldMap() {
    val listState = rememberLazyListState(Int.MAX_VALUE / 2)
    LazyRow(
        modifier = Modifier
            .fillMaxHeight(),
        state = listState
    ) {
        items(Int.MAX_VALUE, itemContent = {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .border(border = BorderStroke(2.dp, Color.Red)),
                painter = painterResource(id = R.drawable.map), contentDescription = "",
                contentScale = ContentScale.FillHeight
            )
        })
    }
}