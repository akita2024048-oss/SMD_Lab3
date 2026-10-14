package uz.ttpu.framephases

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LectureTreeDemo() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .logPhases("Phases", "Row")
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .logPhases("Phases", "Box")
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .logPhases("Phases", "Column")
            ) {
                Text(
                    text = "Compose",
                    modifier = Modifier.logPhases("Phases", "Text1")
                )
                Text(
                    text = "Phases",
                    modifier = Modifier.logPhases("Phases", "Text2")
                )
            }
        }
    }
}
