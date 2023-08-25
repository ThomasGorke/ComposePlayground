@file:OptIn(ExperimentalMaterial3Api::class)

package at.thomasgorke.android.composeexample.ui.basic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.thomasgorke.android.composeexample.R
import at.thomasgorke.android.composeexample.ui.base.TopAppBarContainer
import at.thomasgorke.android.composeexample.ui.destinations.BasicTextFieldScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun BasicButtonScreen(
    navigator: DestinationsNavigator // this will be automatically set by the Compose Destination library
) {
    TopAppBarContainer(
        contentColumnModifier = Modifier
            .fillMaxSize()
            .background(color = Color.Blue)
            .padding(all = 4.dp)
            .background(color = Color.Green)
            .padding(all = 4.dp)
            .background(color = MaterialTheme.colorScheme.background),
        topAppBar = { BasicButtonScreenTopAppBar() }
    ) {
        ButtonOptions()

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            onClick = { navigator.navigate(BasicTextFieldScreenDestination) }
        ) {
            Text(text = "Go to TextFields")
        }
    }
}

@Composable
fun ButtonOptions() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { }) {
            Text(text = "Standard Button")
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Outlined Button")
            }

            FilledTonalButton(onClick = { /*TODO*/ }) {
                Text(text = "Filled Tonal Button")
            }
        }

        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Text Button")
        }

        ElevatedButton(onClick = { /*TODO*/ }) {
            Text(text = "Elevated Button")
        }
    }
}

@Composable
fun BasicButtonScreenTopAppBar() {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.tab_label_basic_buttons)) }
    )
}
