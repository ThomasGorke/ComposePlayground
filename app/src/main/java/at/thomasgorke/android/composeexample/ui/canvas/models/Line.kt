package at.thomasgorke.android.composeexample.ui.canvas.models

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color,
    val strokeWidth: Dp = 5.dp
)

fun List<Line>.removeOutOfBounds(maxY: Float): List<Line> =
    this.filter { line -> !(line.end.y >= maxY || line.start.y >= maxY) }
