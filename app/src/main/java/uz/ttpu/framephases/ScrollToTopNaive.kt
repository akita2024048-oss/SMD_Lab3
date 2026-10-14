package uz.ttpu.framephases

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ScrollToTopNaiveDemo() {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Naive: Reading state directly in Composition scope.
    val showButton = listState.firstVisibleItemIndex > 0

    Log.d("Derived", "ScrollToTopNaive: Composition (showButton = $showButton)")

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            items(200) { index ->
                Text(
                    text = "Item #$index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

        if (showButton) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("Top")
            }
        }
    }
}
