package at.thomasgorke.android.composeexample.ui.data.prefs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.thomasgorke.android.composeexample.ui.base.ColorSlider
import at.thomasgorke.android.composeexample.ui.base.RgbColors
import at.thomasgorke.android.composeexample.ui.data.TabNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@TabNavGraph(start = true)
@Destination
@Composable
fun DataPreferencesScreen(
    navigator: DestinationsNavigator,
    viewModel: DataPreferencesScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        RgbContent(
            red = state.red,
            green = state.green,
            blue = state.blue,
            updateRed = { viewModel.updateRed(it) },
            updateGreen = { viewModel.updateGreen(it) },
            updateBlue = { viewModel.updateBlue(it) }
        )
    }
}

@Composable
fun ColumnScope.RgbContent(
    red: Int,
    green: Int,
    blue: Int,
    updateRed: (Int) -> Unit,
    updateGreen: (Int) -> Unit,
    updateBlue: (Int) -> Unit,
) {
    ColorSlider(color = RgbColors.RED, value = red, newValue = updateRed)
    ColorSlider(color = RgbColors.GREEN, value = green, newValue = updateGreen)
    ColorSlider(color = RgbColors.BLUE, value = blue, newValue = updateBlue)

    Box(
        modifier = Modifier
            .padding(top = 20.dp)
            .height(200.dp)
            .width(200.dp)
            .align(alignment = Alignment.CenterHorizontally)
            .clip(shape = RoundedCornerShape(percent = 50))
            .background(color = Color(red = red, green = green, blue = blue))
    ) {

    }
}