package ru.mironov.compose_examples.ui.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.*
import kotlinx.coroutines.*


@Preview
@Composable
fun TabsPreview(){
    Tabs()
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs() {
    val pagerState = rememberPagerState()

    val pages = listOf("1", "2", "3","4")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            // Our selected tab is our current page
            selectedTabIndex = pagerState.currentPage,
            // Override the indicator, using the provided pagerTabIndicatorOffset modifier
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            // Add tabs for all of our pages
            val scope = rememberCoroutineScope()
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = AnnotatedString(text = title)) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                )
            }
        }

        val colors = listOf(Red, Green, Blue, Yellow, Magenta)

        HorizontalPager(
            state = pagerState,
            count = Int.MAX_VALUE,
            userScrollEnabled = false
        ) { index ->
            val page = index % pages.size
            Text(
                text = AnnotatedString("Page ${pagerState.currentPage + 1}"),
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors[page]),
            )
        }
    }

}