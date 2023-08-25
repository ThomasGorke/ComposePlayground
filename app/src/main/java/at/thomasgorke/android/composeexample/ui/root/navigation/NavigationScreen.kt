package at.thomasgorke.android.composeexample.ui.root.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import at.thomasgorke.android.composeexample.R
import at.thomasgorke.android.composeexample.ui.NavGraphs
import at.thomasgorke.android.composeexample.ui.appCurrentDestinationAsState
import at.thomasgorke.android.composeexample.ui.destinations.BasicButtonScreenDestination
import at.thomasgorke.android.composeexample.ui.destinations.BasicIconsScreenDestination
import at.thomasgorke.android.composeexample.ui.destinations.BasicStateHoistingScreenDestination
import at.thomasgorke.android.composeexample.ui.destinations.BasicTextFieldScreenDestination
import at.thomasgorke.android.composeexample.ui.destinations.CanvasScreenDestination
import at.thomasgorke.android.composeexample.ui.destinations.DataScreenDestination
import at.thomasgorke.android.composeexample.ui.destinations.Destination
import at.thomasgorke.android.composeexample.ui.destinations.DialogScreenDestination
import at.thomasgorke.android.composeexample.ui.destinations.ListScreenDestination
import at.thomasgorke.android.composeexample.ui.startAppDestination
import at.thomasgorke.android.composeexample.utils.SnackerViewModel
import at.thomasgorke.android.composeexample.utils.collectAsEffect
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationScreen(
    snackerViewModel: SnackerViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val currentDestination: Destination = navController.appCurrentDestinationAsState().value
        ?: NavGraphs.root.startAppDestination

    val snackbarHostState = remember { SnackbarHostState() }
    snackerViewModel.snackEffect.collectAsEffect {
        scope.launch {
            snackbarHostState.showSnackbar(message = it.title)
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                BottomBarDestination.values().forEach { destination ->
                    NavigationBarItem(
                        selected = destination.isSelected(currentDestination),
                        onClick = {
                            navController.navigate(destination.direction) {
                                popUpTo(destination.direction) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        label = { Text(text = stringResource(id = destination.label)) },
                        icon = {
                            destination.icon?.let {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = stringResource(id = destination.label)
                                )
                            }
                            destination.resId?.let {
                                Icon(
                                    painter = painterResource(id = it),
                                    contentDescription = stringResource(id = destination.label)
                                )
                            }
                        }
                    )
                }
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier
                        .border(2.dp, MaterialTheme.colorScheme.secondary)
                        .padding(12.dp),
                    action = {
                        TextButton(
                            onClick = { },
                        ) { Text(data.visuals.actionLabel ?: "Hallo") }
                    }
                ) {
                    Text(data.visuals.message)
                }
            }
        }
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            modifier = Modifier.padding(it)
        )
    }
}

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector? = null,
    @DrawableRes val resId: Int? = null,
    @StringRes val label: Int,
    val subDirections: List<DirectionDestinationSpec> = emptyList()
) {
    BASIC(
        direction = BasicButtonScreenDestination,
        icon = Icons.Default.Build,
        label = R.string.tab_label_basic,
        subDirections = listOf(
            BasicTextFieldScreenDestination,
            BasicStateHoistingScreenDestination,
            BasicIconsScreenDestination
        )
    ),
    LIST(
        direction = ListScreenDestination,
        icon = Icons.Default.List,
        label = R.string.tab_label_lists
    ),
    CANVAS(
        direction = CanvasScreenDestination,
        icon = Icons.Default.Create,
        label = R.string.tab_label_canvas,
    ),
    DIALOG(
        direction = DialogScreenDestination,
        resId = R.drawable.ic_message_filled,
        label = R.string.tab_label_dialog
    ),
    DATA(
        direction = DataScreenDestination,
        icon = Icons.Default.CheckCircle,
        label = R.string.tab_label_data
    );

    fun isSelected(currentDestination: Destination): Boolean =
        currentDestination == direction || subDirections.any { it == currentDestination }
}
