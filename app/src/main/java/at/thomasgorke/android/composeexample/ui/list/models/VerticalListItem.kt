package at.thomasgorke.android.composeexample.ui.list.models

data class VerticalListItem(
    val title: String,
    val subtitle: String
)

val verticalList = listOf(
    VerticalListItem(
        title = "Item 1",
        subtitle = "Content of an Item"
    ),
    VerticalListItem(
        title = "Item 2",
        subtitle = "Content of an Item"
    ),
    VerticalListItem(
        title = "Item 3",
        subtitle = "Content of an Item"
    ),
    VerticalListItem(
        title = "Item 4",
        subtitle = "Content of an Item"
    ),
    VerticalListItem(
        title = "Item 5",
        subtitle = "Content of an Item"
    ),
    VerticalListItem(
        title = "Item 6",
        subtitle = "Content of an Item"
    )
)
