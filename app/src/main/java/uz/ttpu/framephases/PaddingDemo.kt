package uz.ttpu.framephases

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun PaddingDemo() {
    var paddingDp by remember { mutableStateOf(8.dp) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            paddingDp = if (paddingDp == 8.dp) 32.dp else 8.dp
        }) {
            Text("Toggle padding (Current: $paddingDp)")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Padding Demo Box",
            modifier = Modifier
                .padding(paddingDp) // State read happens during Composition!
                .background(Color.LightGray)
                .logPhases("Phases", "PaddingText")
        )
    }
}
