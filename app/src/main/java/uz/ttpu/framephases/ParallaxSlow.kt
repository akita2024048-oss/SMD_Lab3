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
import androidx.compose.ui.unit.dp

@Composable
fun ParallaxSlowDemo() {
    val listState = rememberLazyListState()

    // Log recompositions with tag "Parallax"
    Log.d("Parallax", "ParallaxSlow: Composition")

    LazyColumn(state = listState) {
        item {
            // Reading listState.firstVisibleItemScrollOffset directly in Composition scope!
            val scrollOffset = listState.firstVisibleItemScrollOffset
            val parallaxY = (scrollOffset * 0.5f).toInt()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .offset(y = parallaxY.dp) // State read in composition phase
                    .background(Color.DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Parallax Header (Slow - Direct Read)",
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
