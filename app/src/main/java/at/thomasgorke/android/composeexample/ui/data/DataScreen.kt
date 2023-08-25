@file:OptIn(ExperimentalMaterial3Api::class)

package at.thomasgorke.android.composeexample.ui.data

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import at.thomasgorke.android.composeexample.R
import at.thomasgorke.android.composeexample.ui.NavGraphs
import at.thomasgorke.android.composeexample.ui.appCurrentDestinationAsState
import at.thomasgorke.android.composeexample.ui.destinations.DataPreferencesScreenDestination
import at.thomasgorke.android.composeexample.ui.destinations.DataRetrofitScreenDestination
import at.thomasgorke.android.composeexample.ui.destinations.DataRoomScreenDestination
import at.thomasgorke.android.composeexample.ui.destinations.Destination
import at.thomasgorke.android.composeexample.ui.startAppDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun DataScreen(
    navigator: DestinationsNavigator
) {
    val tabNavController = rememberNavController()
    val currentDestination: Destination = tabNavController.appCurrentDestinationAsState().value
        ?: NavGraphs.tab.startAppDestination

    // Possible AppBar behaviors (choose what you like most):
//    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = stringResource(id = R.string.tab_label_data)) }
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            TabRow(selectedTabIndex = tabs.indexOfFirst { it.destination == currentDestination }) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = tabs.indexOfFirst { it.destination == currentDestination } == index,
                        onClick = {
                            tabNavController.navigate(tab.destination) {
                                popUpTo(tab.destination) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        text = { Text(text = tab.title) },
                        icon = {
                            Icon(
                                painter = painterResource(id = tab.iconId),
                                contentDescription = null
                            )
                        }
                    )
                }
            }

            DestinationsNavHost(
                navGraph = NavGraphs.tab,
                navController = tabNavController
            )
        }
    }
}

data class TabItem(
    val destination: DirectionDestinationSpec,
    val title: String,
    @DrawableRes val iconId: Int
)

val tabs = listOf(
    TabItem(
        destination = DataPreferencesScreenDestination,
        title = "Prefs",
        iconId = R.drawable.ic_prefs
    ),
    TabItem(
        destination = DataRoomScreenDestination,
        title = "Room",
        iconId = R.drawable.ic_room
    ),
    TabItem(
        destination = DataRetrofitScreenDestination,
        title = "Retrofit",
        iconId = R.drawable.ic_api
    )
)
