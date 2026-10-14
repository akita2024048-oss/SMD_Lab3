package uz.ttpu.framephases

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class DemoOption(val title: String) {
    LECTURE_TREE("Q4: First Frame (LectureTree)"),
    PADDING_DEMO("Q5: Composition Read (Padding)"),
    OFFSET_DEMO("Q6: Layout Read (Offset)"),
    COLOR_DEMO("Q7: Draw Read (Color/Canvas)"),
    PARALLAX_SLOW("Q9: Parallax Slow (Direct Read)"),
    PARALLAX_FAST("Q9: Parallax Fast (Lambda Read)"),
    SCROLL_TOP_NAIVE("Q10: ScrollToTop Naive"),
    SCROLL_TOP_DERIVED("Q10: ScrollToTop Derived"),
    SKIP_DEMO("Q11: Skipping & Deferred Reads"),
    CYCLIC_DEMO("Q13: Cyclic Layout (Issue)"),
    CYCLIC_FIXED("Q13: Cyclic Layout (Fixed)"),
    STACK_BELOW("Q14: Custom Layout (StackBelow)")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                MainContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    var selectedDemo by remember { mutableStateOf(DemoOption.LECTURE_TREE) }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Dropdown selection bar for Practice 7 demos
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextField(
                    value = selectedDemo.title,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Practice 7 Demo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DemoOption.entries.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option.title) },
                            onClick = {
                                selectedDemo = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Display selected demo
            when (selectedDemo) {
                DemoOption.LECTURE_TREE -> LectureTreeDemo()
                DemoOption.PADDING_DEMO -> PaddingDemo()
                DemoOption.OFFSET_DEMO -> OffsetDemo()
                DemoOption.COLOR_DEMO -> ColorDemo()
                DemoOption.PARALLAX_SLOW -> ParallaxSlowDemo()
                DemoOption.PARALLAX_FAST -> ParallaxFastDemo()
                DemoOption.SCROLL_TOP_NAIVE -> ScrollToTopNaiveDemo()
                DemoOption.SCROLL_TOP_DERIVED -> ScrollToTopDerivedDemo()
                DemoOption.SKIP_DEMO -> SkipDemo()
                DemoOption.CYCLIC_DEMO -> CyclicDemo()
                DemoOption.CYCLIC_FIXED -> CyclicFixedDemo()
                DemoOption.STACK_BELOW -> StackBelowDemo()
            }
        }
    }
}
