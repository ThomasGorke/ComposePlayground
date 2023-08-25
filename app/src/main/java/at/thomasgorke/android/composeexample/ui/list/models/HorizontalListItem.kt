package at.thomasgorke.android.composeexample.ui.list.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.graphics.vector.ImageVector

data class HorizontalListItem(
    val title: String,
    val icon: ImageVector
)

val horizontalListItems = listOf(
    HorizontalListItem(
        title = "Home",
        icon = Icons.Default.Home
    ),
    HorizontalListItem(
        title = "Phone",
        icon = Icons.Default.Phone
    ),
    HorizontalListItem(
        title = "Person",
        icon = Icons.Default.Person
    ),
    HorizontalListItem(
        title = "List",
        icon = Icons.Default.List
    ),
    HorizontalListItem(
        title = "Check",
        icon = Icons.Default.Check
    ),
    HorizontalListItem(
        title = "Build",
        icon = Icons.Default.Build
    )
)
