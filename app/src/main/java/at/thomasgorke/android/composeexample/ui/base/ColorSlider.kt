package at.thomasgorke.android.composeexample.ui.base

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorSlider(
    color: RgbColors,
    value: Int,
    newValue: (Int) -> Unit
) {
    Slider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        value = value.toFloat(),
        onValueChange = { updatedValue ->
            newValue(updatedValue.toInt())
        },
        valueRange = 0f..255f,
        colors = SliderDefaults.colors(
            thumbColor = color.intenseColor(),
            activeTrackColor = color.intenseColor(),
            inactiveTrackColor = color.alphaColor()
        )
    )
}

enum class RgbColors {
    RED, GREEN, BLUE;

    fun intenseColor(): Color = when (this) {
        RED -> Color.Red
        GREEN -> Color.Green
        BLUE -> Color.Blue
    }

    fun alphaColor(): Color = when (this) {
        RED -> Color.Red.copy(alpha = 0.3f)
        GREEN -> Color.Green.copy(alpha = 0.3f)
        BLUE -> Color.Blue.copy(alpha = 0.3f)
    }
}