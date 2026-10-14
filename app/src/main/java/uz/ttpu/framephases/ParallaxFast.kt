package uz.ttpu.framephases

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun ParallaxFastDemo() {
    val listState = rememberLazyListState()

    // Log recompositions with tag "Parallax"
    Log.d("Parallax", "ParallaxFast: Composition")

    LazyColumn(state = listState) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .offset {
                        // Defer state read to Layout/Placement phase via lambda!
                        val scrollOffset = listState.firstVisibleItemScrollOffset
                        IntOffset(0, (scrollOffset * 0.5f).toInt())
                    }
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Parallax Header (Fast - Lambda Deferred)",
                    color = Color.White
                )
            }
        }

        items(100) { index ->
            Text(
                text = "Item #$index",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
