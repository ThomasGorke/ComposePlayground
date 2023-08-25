@file:OptIn(ExperimentalMaterial3Api::class)

package at.thomasgorke.android.composeexample.ui.basic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.thomasgorke.android.composeexample.R
import at.thomasgorke.android.composeexample.ui.base.TopAppBarContainer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.runBlocking

@Destination
@Composable
fun BasicIconsScreen(
    navigator: DestinationsNavigator
) {
    var callCount by rememberSaveable {
        mutableIntStateOf(0)
    }

    TopAppBarContainer(
        topAppBar = { BasicIconsScreenTopAppBar(navigator = navigator, count = callCount) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Veränder den Wert der Badge\nCurrent value: ${callCount}")

            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                IconButton(onClick = { callCount-- }) {
                    Icon(painter = painterResource(id = R.drawable.ic_min_1), contentDescription = "Subtract")
                }

                IconButton(
                    onClick = { callCount++ }
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_add_1), contentDescription = "Add")
                }
            }
        }
    }
}

@Composable
fun BasicIconsScreenTopAppBar(
    navigator: DestinationsNavigator,
    count: Int
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.tab_label_basic_icons))
        },
        navigationIcon = {
            IconButton(onClick = { navigator.navigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                BadgedBox(
                    badge = {
                        when {
                            count > 0 -> Badge { Text(text = "$count") }
                            count == 0 -> { /* do nothing */ }
                            else -> Badge()
                        }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = "Calls")
                }
            }
        }
    )
}

fun main() {
    runBlocking {


    }
}