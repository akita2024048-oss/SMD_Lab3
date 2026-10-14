package uz.ttpu.framephases

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun OffsetDemo() {
    var offsetPx by remember { mutableIntStateOf(0) }
    var useLambda by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { offsetPx += 20 }) {
            Text("Move (offset: ${offsetPx}px)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { useLambda = !useLambda; offsetPx = 0 }) {
            Text("Mode: ${if (useLambda) "Lambda (Layout phase read)" else "Direct Value (Composition read)"}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!useLambda) {
            // Direct Value Offset: State read during Composition
            Box(
                modifier = Modifier
                    .offset(y = offsetPx.dp)
                    .size(80.dp)
                    .background(Color.Cyan)
                    .logPhases("Phases", "CompositionOffsetBox")
            )
        } else {
            // Lambda Offset: State read deferred to Layout / Placement phase
            Box(
                modifier = Modifier
                    .offset { IntOffset(0, offsetPx) }
                    .size(80.dp)
                    .background(Color.Magenta)
                    .logPhases("Phases", "LambdaOffsetBox")
            )
        }
    }
}
