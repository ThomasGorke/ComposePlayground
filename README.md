# Compose Playground

This project uses many compose elements to see what is currently possible and how elements can interact with others...

## Elements used:
* AlertDialogs
* Badges
* Bottomsheet
* Buttons
* Grids
* Icons
* Scaffold
* Slider
* Snackbar
* Tabs
* Textfields
* TopAppBar
* (Lazy-)Columns
* (Lazy-)Rows
* (Bottom) NavigationBar

## Used Frameworks
* Compose Destinations
* Datastore
* Gson
* Koin
* Retrofit
* Room

## Architecture
Where data gets fetched from an API, Room or Datastore an MVVM/MVI Pattern is implemented when communicating with the UI. For the data related classes an light weight repository pattern was used.

