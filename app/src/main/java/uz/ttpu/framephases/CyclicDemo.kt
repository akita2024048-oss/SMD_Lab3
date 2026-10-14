package uz.ttpu.framephases

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun CyclicDemo() {
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    Log.d("Cycle", "CyclicDemo: Composition (boxSize = $boxSize)")

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Cyclic Layout Demo (State loop problem)")

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFF800080)) // Purple box
                .onSizeChanged { size ->
                    Log.d("Cycle", "CyclicDemo: onSizeChanged = $size")
                    // Writing state in layout pass forces a second frame / recomposition!
                    boxSize = size
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Box size measured: width=${boxSize.width}, height=${boxSize.height}")
    }
}
