package at.thomasgorke.android.composeexample.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle


// Read here: https://composedestinations.rafaelcosta.xyz/styles-and-animations?_highlight=dialog#dialog-style
@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun ComposeDestinationsDialog(navigator: DestinationsNavigator) {
    AlertDialog(
        title = { Text(text = "Compose Destinations Dialog") },
        text = { Text(text = "The dialog is handled like a default destination") },
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = { navigator.navigateUp() }
            ) {
                Text(text = "OK")
            }
        },
        icon = {
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
        }
    )
}

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun CustomDialog(navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(size = 16.dp))
            .background(color = Color.Magenta)
            .padding(all = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Warning, contentDescription = null)
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Custom Dialog",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "I am the content of the dialog. If I wanted I could have added an image here.",
            style = MaterialTheme.typography.bodyMedium
        )

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { navigator.navigateUp() }) {
                Text(text = "OK")
            }
        }
    }
}
