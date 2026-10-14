package uz.ttpu.framephases

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun StackBelow(
    modifier: Modifier = Modifier,
    spacing: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val spacingPx = spacing.roundToPx()

        // 1. Measure each child exactly once
        val placeables = measurables.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)
            Log.d(
                "StackBelow",
                "Measured child $index: width=${placeable.width}, height=${placeable.height}"
            )
            placeable
        }

        // 2. Calculate max child width and total height plus spacing
        val maxWidth = placeables.maxOfOrNull { it.width } ?: 0
        val totalSpacing = if (placeables.isNotEmpty()) spacingPx * (placeables.size - 1) else 0
        val totalHeight = placeables.sumOf { it.height } + totalSpacing

        // 3. Layout and place children vertically with spacing
        layout(maxWidth, totalHeight) {
            var yPosition = 0
            placeables.forEachIndexed { index, placeable ->
                Log.d("StackBelow", "Placed child $index at y=$yPosition")
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height + spacingPx
            }
        }
    }
}

@Composable
fun StackBelowDemo() {
    var childCount by remember { mutableIntStateOf(2) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            childCount = if (childCount == 2) 3 else 2
        }) {
            Text("Toggle Children Count (Current: $childCount)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        StackBelow(spacing = 8.dp) {
            Box(
                modifier = Modifier
                    .size(width = 120.dp, height = 40.dp)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .size(width = 160.dp, height = 50.dp)
                    .background(Color.Green)
            )
            if (childCount >= 3) {
                Box(
                    modifier = Modifier
                        .size(width = 100.dp, height = 60.dp)
                        .background(Color.Blue)
                )
            }
        }
    }
}
