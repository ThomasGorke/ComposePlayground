@file:OptIn(ExperimentalMaterial3Api::class)

package at.thomasgorke.android.composeexample.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import at.thomasgorke.android.composeexample.R
import at.thomasgorke.android.composeexample.ui.base.TopAppBarContainer
import at.thomasgorke.android.composeexample.ui.destinations.ComposeDestinationsDialogDestination
import at.thomasgorke.android.composeexample.ui.destinations.CustomDialogDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DialogScreen(navigator: DestinationsNavigator) {
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    TopAppBarContainer(
        topAppBar = { DialogScreenTopAppBar() }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier
                    .padding(top = 16.dp),
                onClick = { showDialog = true }
            ) {
                Text(text = "Show Dialog")
            }

            Button(
                modifier = Modifier
                    .padding(top = 8.dp),
                onClick = {
                    navigator.navigate(ComposeDestinationsDialogDestination)
                }
            ) {
                Text(text = "Show Compose Destinations Dialog")
            }

            Button(
                modifier = Modifier
                    .padding(top = 8.dp),
                onClick = {
                    navigator.navigate(CustomDialogDestination)
                }
            ) {
                Text(text = "Show Custom Dialog")
            }
        }

        if (showDialog) {
            AlertDialog(
                title = { Text(text = "Simple Dialog") },
                text = { Text(text = "The dialog gets triggered by a simple mutable property that gets toggled by pressing the button") },
                onDismissRequest = {
                    // This gets triggered when tapping outside of the dialog -> leave this empty
                    // if the dialog should not be dismissable
                    showDialog = false
                },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = "OK")
                    }
                },
                icon = {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
                }
            )
        }
    }
}

@Composable
fun DialogScreenTopAppBar() {
    TopAppBar(title = { Text(text = stringResource(id = R.string.tab_label_dialog)) })
}