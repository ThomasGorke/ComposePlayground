package at.thomasgorke.android.composeexample.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.thomasgorke.android.composeexample.R
import at.thomasgorke.android.composeexample.ui.base.BackTopAppBar
import at.thomasgorke.android.composeexample.ui.base.ColorSlider
import at.thomasgorke.android.composeexample.ui.base.RgbColors
import at.thomasgorke.android.composeexample.ui.base.TopAppBarContainer
import at.thomasgorke.android.composeexample.ui.destinations.BasicIconsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun BasicStateHoistingScreen(navigator: DestinationsNavigator) {
    var red by remember {
        mutableIntStateOf(0) // mutableState tells compose that this property can be changed and that a recomposition is necessary
    }

    var green by rememberSaveable { // rememberSaveable also saves data when this composable gets completely new painted (e.g. screen rotation)
        mutableIntStateOf(0)
    }

    var blue by remember {
        mutableIntStateOf(0)
    }

    TopAppBarContainer(
        topAppBar = { BasicStateHoistingScreenTopAppBar(navigator = navigator) }
    ) {
        ColorSlider(color = RgbColors.RED, value = red) { red = it }
        ColorSlider(color = RgbColors.GREEN, value = green) { green = it }
        ColorSlider(color = RgbColors.BLUE, value = blue) { blue = it }

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

        Button(
            modifier = Modifier.padding(top = 20.dp).align(alignment = Alignment.CenterHorizontally),
            onClick = { navigator.navigate(BasicIconsScreenDestination) }) {
            Text(text = "Go to icons")
        }
    }
}


@Composable
fun BasicStateHoistingScreenTopAppBar(navigator: DestinationsNavigator) {
    BackTopAppBar(title = stringResource(id = R.string.tab_label_basic_state_hoisting)) {
        navigator.navigateUp()
    }
}