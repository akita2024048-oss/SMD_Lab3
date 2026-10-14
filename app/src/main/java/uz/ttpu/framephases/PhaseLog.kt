package uz.ttpu.framephases

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.layout.layout

/**
 * Extension modifier to log Composition, Measurement, Placement, and Drawing phases.
 */
@Composable
fun Modifier.logPhases(tag: String = "Phases", name: String): Modifier {
    // Log Composition phase (runs during composable evaluation)
    Log.d(tag, "$name: Composition")

    return this
        .layout { measurable, constraints ->
            // Log Measure phase
            Log.d(tag, "$name: Measure")
            val placeable = measurable.measure(constraints)
            layout(placeable.width, placeable.height) {
                // Log Place phase
                Log.d(tag, "$name: Place")
                placeable.placeRelative(0, 0)
            }
        }
        .drawWithContent {
            // Log Draw phase
            Log.d(tag, "$name: Draw")
            drawContent()
        }
}
