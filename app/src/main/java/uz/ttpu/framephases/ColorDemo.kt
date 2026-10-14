package uz.ttpu.framephases

import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorDemo() {
    var colorState by remember { mutableStateOf(Color.Red) }
    var useCanvas by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            colorState = if (colorState == Color.Red) Color.Blue else Color.Red
        }) {
            Text("Change Color (Current: ${if (colorState == Color.Red) "Red" else "Blue"})")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { useCanvas = !useCanvas }) {
            Text("Mode: ${if (useCanvas) "Canvas / drawRect (Draw phase read)" else "background(Color) (Composition read)"}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!useCanvas) {
            // background(Color): Read state during composition
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(colorState)
                    .logPhases("Phases", "BackgroundBox")
            )
        } else {
            // Canvas / drawRect: Read state inside draw scope
            Canvas(
                modifier = Modifier
                    .size(100.dp)
                    .logPhases("Phases", "CanvasBox")
            ) {
                // State read deferred to Draw phase!
                drawRect(color = colorState)
            }
        }
    }
}
