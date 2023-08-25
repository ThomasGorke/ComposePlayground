@file:OptIn(ExperimentalMaterial3Api::class)

package at.thomasgorke.android.composeexample.ui.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.thomasgorke.android.composeexample.R
import at.thomasgorke.android.composeexample.ui.base.TopAppBarContainer
import at.thomasgorke.android.composeexample.ui.canvas.models.Line
import at.thomasgorke.android.composeexample.ui.canvas.models.Pie
import at.thomasgorke.android.composeexample.ui.canvas.models.removeOutOfBounds
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CanvasScreen(navigator: DestinationsNavigator) {
    TopAppBarContainer(
        topAppBar = { CanvasScreenTopAppBar() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(.5f)
                    .fillMaxWidth()
                    .border(
                        width = 5.dp,
                        color = Color.LightGray
                    )
            ) {
                TouchPainter()

                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.BottomCenter),
                    text = "Draw something",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Box(modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    val pies = listOf(
                        Pie(color = Color.Green, percentage = 50),
                        Pie(color = Color.Yellow, percentage = 2),
                        Pie(color = Color.Red, percentage = 20),
                        Pie(color = Color.Blue, percentage = 5),
                    )

                    pies.forEachIndexed { index, pie ->
                        drawArc(
                            size = Size(400f, 400f),
                            color = pie.color,
                            startAngle = (360f / 100) * pies
                                .take(index)
                                .sumOf { it.percentage },
                            sweepAngle = (360f / 100) * pie.percentage,
                            useCenter = true,
                            topLeft = Offset((size.width / 2) - 200f, (size.height / 2) - 200f)
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun TouchPainter() {
    val localDensity = LocalDensity.current

    val lines = remember {
        mutableStateListOf<Line>()
    }

    var height by remember {
        mutableStateOf(0.dp)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val start = change.position - dragAmount
                        val end = change.position
                        val line = Line(start = start, end = end, color = Color.Red)
                        lines += line
                    }
                }
                .onGloballyPositioned { coordinates ->
                    height = with(localDensity) { coordinates.size.height.toDp() }
                }
        ) {
            lines
                .removeOutOfBounds(height.toPx())
                .forEach { line ->
                    drawLine(
                        color = line.color,
                        start = line.start,
                        end = line.end,
                        strokeWidth = line.strokeWidth.toPx()
                    )
                }
        }

        IconButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = { lines.clear() }
        ) {
            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
        }
    }
}

@Composable
fun CanvasScreenTopAppBar() {
    TopAppBar(title = { Text(text = stringResource(id = R.string.tab_label_canvas)) })
}
