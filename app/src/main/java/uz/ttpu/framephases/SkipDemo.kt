package uz.ttpu.framephases

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SkipDemo() {
    var count by remember { mutableIntStateOf(0) }
    var useLambda by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { count++ }) {
            Text("Increment Count (Count = $count)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { useLambda = !useLambda; count = 0 }) {
            Text("Mode: ${if (useLambda) "Lambda () -> Int" else "Value Int"}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!useLambda) {
            ParentValueMode(count = count)
        } else {
            ParentLambdaMode(countProvider = { count })
        }
    }
}

@Composable
fun ParentValueMode(count: Int) {
    Log.d("Skip", "ParentValueMode: Composition")
    Column {
        ChildValue(count = count)
        Sibling()
    }
}

@Composable
fun ChildValue(count: Int) {
    Log.d("Skip", "ChildValue: Composition (count = $count)")
    Text("Child Value: $count")
}

@Composable
fun ParentLambdaMode(countProvider: () -> Int) {
    Log.d("Skip", "ParentLambdaMode: Composition")
    Column {
        ChildLambda(countProvider = countProvider)
        Sibling()
    }
}

@Composable
fun ChildLambda(countProvider: () -> Int) {
    val count = countProvider()
    Log.d("Skip", "ChildLambda: Composition (count = $count)")
    Text("Child Lambda: $count")
}

@Composable
fun Sibling() {
    Log.d("Skip", "Sibling: Composition")
    Text("Sibling (Static)")
}
