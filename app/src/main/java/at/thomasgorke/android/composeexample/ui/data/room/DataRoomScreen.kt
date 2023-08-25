package at.thomasgorke.android.composeexample.ui.data.room

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.thomasgorke.android.composeexample.R
import at.thomasgorke.android.composeexample.data.model.Contact
import at.thomasgorke.android.composeexample.ui.data.TabNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@TabNavGraph
@Destination
@Composable
fun DataRoomScreen(
    navigator: DestinationsNavigator,
    viewModel: DataRoomScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val listState = rememberLazyListState()

    LaunchedEffect(key1 = state.contacts.size) {
        if (state.contacts.isNotEmpty()) {
            delay(200)
            listState.animateScrollToItem(state.contacts.size - 1)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = listState
        ) {
            items(state.contacts) {
                ContactItem(contact = it) {
                    // TODO show bottom sheet maybe
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = 16.dp),
            onClick = { viewModel.insertNewContact() }
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_add_contact), contentDescription = "Add contact")
        }
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    click: (Contact) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 16.dp))
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
            .clickable { click(contact) }
            .padding(all = 12.dp)
    ) {
        Text(
            text = contact.name,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = contact.email,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

