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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CyclicFixedDemo() {
    Log.d("Cycle", "CyclicFixedDemo: Composition")

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Cyclic Layout Fixed (Standard Column layout)")

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color(0xFF800080)) // Purple box
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Box size is controlled by layout constraints without state dependency!")
    }
}
