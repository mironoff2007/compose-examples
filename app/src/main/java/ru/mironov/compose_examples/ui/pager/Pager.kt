package ru.mironov.compose_examples.ui.pager

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@Preview
@Composable
fun TabsPreview() {
    Tabs()
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs() {
    val pagerState = rememberPagerState()

    val pages = listOf("Tab 1", "Tab 2", "Tab 3", "Tab 4", "Tab 5", "Tab 6")

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScrollableTabRow(
            modifier = Modifier.wrapContentHeight(),
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            val scope = rememberCoroutineScope()
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = AnnotatedString(text = title)) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            try {
                                pagerState.animateScrollToPage(index)
                            } catch (e: Exception) {
                                Log.d("UI_tag", e.stackTraceToString())
                            }
                        }
                    },
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            count = Int.MAX_VALUE,
            userScrollEnabled = false
        ) { index ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = AnnotatedString("Page ${pagerState.currentPage + 1}"), fontSize = 35.sp)
            }
        }
    }

}