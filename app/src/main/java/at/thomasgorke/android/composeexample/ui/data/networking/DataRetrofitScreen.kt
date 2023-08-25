@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package at.thomasgorke.android.composeexample.ui.data.networking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import at.thomasgorke.android.composeexample.data.RepositoryResponse
import at.thomasgorke.android.composeexample.ui.data.TabNavGraph
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@TabNavGraph
@Destination
@Composable
fun DataRetrofitScreen(
    navigator: DestinationsNavigator,
    viewModel: DataRetrofitScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        when {
            state.hasError -> NetworkErrorScreen {
                viewModel.reload()
            }
            state.isLoading -> LoadingScreen()
            else -> {
                SuccessScreen(breeds = state.breeds) { name ->
                    viewModel.loadImageOfBreed(name)
                }

                if (state.imageUrl.isNotEmpty()) {
                    AlertDialog(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(size = 28.dp))
                            .background(MaterialTheme.colorScheme.background),
                        onDismissRequest = {
                            viewModel.closeDialog()
                        },
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                modifier = Modifier.fillMaxWidth(),
                                model = state.imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.FillWidth
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SuccessScreen(
    breeds: List<String>,
    click: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 8.dp)
    ) {
        breeds
            .groupBy { it.first() }
            .forEach { (letter, names) ->
                stickyHeader {
                    BreedHeaderItem(letter = letter.toString())
                }
                names.forEach {
                    item {
                        BreedItem(name = it) { name ->
                            click(name)
                        }
                    }
                }
            }
    }
}

@Composable
fun BreedHeaderItem(
    letter: String
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()

            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = 20.dp, vertical = 8.dp),
        style = MaterialTheme.typography.headlineSmall,
        text = letter,
        color = MaterialTheme.colorScheme.onSecondaryContainer
    )
}

@Composable
fun BreedItem(
    name: String,
    click: (String) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { click(name) }
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(all = 16.dp),
        style = MaterialTheme.typography.labelLarge,
        text = name,
        color = MaterialTheme.colorScheme.onTertiaryContainer
    )
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun NetworkErrorScreen(
    retry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.labelMedium,
            text = "Error while loading data..."
        )

        FilledTonalButton(
            modifier = Modifier.padding(top = 20.dp),
            onClick = retry
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Retry")
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Reload"
            )
        }
    }
}