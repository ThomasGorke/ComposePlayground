@file:OptIn(ExperimentalMaterial3Api::class)

package at.thomasgorke.android.composeexample.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import at.thomasgorke.android.composeexample.ui.base.TopAppBarContainer
import at.thomasgorke.android.composeexample.ui.list.models.horizontalListItems
import at.thomasgorke.android.composeexample.ui.list.models.verticalList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * Hint: Actually it is not possible to have something like nested scrolling (scrollview within
 * another scrollview where both of them are scrollable in the same direction). In the example
 * below it would not be possible to wrap all
 */

@Destination
@Composable
fun ListScreen(navigator: DestinationsNavigator) {
    TopAppBarContainer(
        topAppBar = { ListScreenTopAppBar() }
    ) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            horizontalListItems.forEach {
                item {
                    HorizontalListItem(
                        label = it.title,
                        icon = it.icon
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(count = 3),
            contentPadding = PaddingValues(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            horizontalListItems.forEach {
                item {
                    GridItem(text = it.title)
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            verticalList.forEach {
                item {
                    VerticalListItem(
                        title = it.title,
                        subtitle = it.subtitle
                    )
                }
            }
        }
    }
}

@Composable
fun GridItem(text: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(shape = RoundedCornerShape(size = 8.dp))
            .background(color = Color.LightGray)
            .padding(16.dp),
        text = text
    )
}

@Composable
fun HorizontalListItem(
    label: String,
    icon: ImageVector
) {
    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(percent = 50))
            .background(color = Color.LightGray)
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = label)
        Text(text = label)
    }
}

@Composable
fun VerticalListItem(
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp)) // should be the same as the shape of the card to have correct highlighting when clicking
            .background(color = Color.LightGray)
            .clickable { }
            .padding(16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ListScreenTopAppBar() {
    TopAppBar(title = { Text(text = "Lists") })
}

