package at.thomasgorke.android.composeexample.ui.data

import at.thomasgorke.android.composeexample.ui.data.networking.DataRetrofitScreenViewModel
import at.thomasgorke.android.composeexample.ui.data.prefs.DataPreferencesScreenViewModel
import at.thomasgorke.android.composeexample.ui.data.room.DataRoomScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val dataScreenModule = module {
    viewModel { DataPreferencesScreenViewModel(repository = get()) }
    viewModel { DataRetrofitScreenViewModel(repository = get(), snacker = get()) }
    viewModel { DataRoomScreenViewModel(repository = get()) }
}